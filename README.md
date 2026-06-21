# 📚 LibraFlow

> **Biblioteczny system zarządzania wypożyczeniami** zbudowany na zasadach Domain-Driven Design, architektury sześciokątnej (Hexagonal/Ports & Adapters), Service-Oriented Architecture (SOA) oraz Clean Architecture.

---

## Opis projektu

**LibraFlow** to system informatyczny wspierający pracę biblioteki publicznej. Jego głównym celem jest **automatyczne egzekwowanie polityki wypożyczeń** — bez udziału człowieka system wykrywa naruszenia, nakłada kary finansowe i blokuje konta czytelników, którzy przekroczyli dopuszczalne limity lub przetrzymali egzemplarze.

Projekt powstał jako implementacja akademicka zasad **DDD + Hexagonal + SOA + Clean Architecture**, demonstrując praktyczne zastosowanie wzorców Context Map (Shared Kernel, Customer/Supplier, Anti-Corruption Layer, Open Host Service) w systemie wielokontekstowym.

---

## Architektura

System oparty jest na czterech filarach architektonicznych:

**Domain-Driven Design (DDD)** — Każdy Bounded Context posiada własny Ubiquitous Language, model dziedziny i logikę biznesową. Wzorce taktyczne (Agregaty, Encje, Value Objects, Domain Events) chronią niezmienniki domenowe.

**Hexagonal Architecture (Ports & Adapters)** — Logika biznesowa jest całkowicie izolowana od szczegółów technicznych (frameworki, bazy danych, zewnętrzne API). Komunikacja odbywa się wyłącznie przez porty i adaptery.

**Service-Oriented Architecture (SOA)** — System podzielony jest na niezależne usługi biznesowe o luźnych powiązaniach, komunikujące się przez dobrze zdefiniowane kontrakty (interfejsy, zdarzenia domenowe).

**Clean Architecture** — Wszystkie zależności prowadzą do środka — w kierunku kodu dziedziny. Warstwa konfiguracyjna składa system z niezależnych kontekstów.

---

## Poddziedziny (Subdomains)

| Poddziedzina | Typ | Opis |
|---|---|---|
| **System Wypożyczeń** | Core Domain | Polityka blokad, kar i cykl życia wypożyczeń. Kluczowa przewaga biznesowa. |
| **Zarządzanie Katalogiem** | Supporting Domain | Definicje książek, egzemplarze, dostępność zasobów. |
| **Zarządzanie Tożsamością** | Generic Domain | Konta, profile, role użytkowników. |
| **System Powiadomień** | Generic Domain | Asynchroniczne powiadomienia e-mail/SMS. |
| **Zarządzanie Płatnościami** | Generic Domain | Integracja z zewnętrznym operatorem płatności (np. PayU/Stripe). |

---

## Bounded Contexts

### Lending Context (Core)
Centrum systemu. Odpowiada za pełny cykl życia wypożyczenia.

Kluczowe pojęcia: `Reader`, `BookCopy`, `src.main.java.Loan`, `Fine`, `Blockade`

### Catalog Context (Supporting)
Źródło prawdy o zasobach bibliotecznych.

Kluczowe pojęcia: `Book`, `BookCopy` (inna definicja niż w Lending!)

### Identity Context (Generic)
Centralny moduł tożsamości.

Kluczowe pojęcia: `UserAccount`, `Profile`, `Role`

### Payment Context (Generic)
Fasada nad zewnętrznym operatorem płatności.

Kluczowe pojęcia: `Transaction`, `PaymentMethod`, `PaymentStatus`, `PaymentGateway`

### Notification Context (Generic)
Asynchroniczny kanał komunikacji z użytkownikami.

Kluczowe pojęcia: `Recipient`, `Channel`, `MessageTemplate`, `NotificationMessage`

---

## Context Map — wzorce integracji

Relacje między kontekstami zgodne z [Context Map Patterns](https://blog.przemyslawsobolewski.com/context-map-patterns-w-domain-driven-design/) (Sobolewski, 2025):

| Upstream (dostawca) | Downstream (odbiorca) | Wzorzec | Uzasadnienie |
|---|---|---|---|
| Identity Context | Lending Context | **Customer/Supplier** | Lending zgłasza wymagania (np. zdarzenie `AccountCreated`); Identity je uwzględnia. |
| Catalog Context | Lending Context | **Customer/Supplier** | Catalog informuje o nowych egzemplarzach; Lending inicjuje ich status. |
| Lending Context | Payment Context | **Open Host Service + Published Language** | Lending wystawia kontrakt API do inicjowania opłat; Payment go implementuje. |
| Lending Context | Notification Context | **Open Host Service + Published Language** | Lending publikuje zdarzenia (`FineIssued`, `LoanOverdue`); Notification subskrybuje. |
| Shared Kernel | Lending + Catalog | **Shared Kernel** | Współdzielone Value Objects: `BookCopyId`, `Money`, `DateRange`. |
| Payment Context (zewn.) | Payment Context | **Anti-Corruption Layer (ACL)** | ACL tłumaczy model zewnętrznego PayU/Stripe na wewnętrzny model dziedziny. |

> **Shared Kernel** zawiera wyłącznie stabilne, fundamentalne obiekty wartości (`Money`, `BookCopyId`). Zgodnie z zasadami praktycznymi: minimalizm, wspólna odpowiedzialność, stabilność i testy kontraktowe.

---

## Struktura warstw (Hexagonal — jeden kontekst)

```
lending-context/
├── infrastructure/          # Warstwa infrastruktury (adaptery)
│   ├── input/               # Adaptery wejściowe (REST, CLI, Message Consumer)
│   └── output/              # Adaptery wyjściowe (JPA, Event Publisher, HTTP Client)
├── application/             # Warstwa aplikacji
│   ├── services/            # Usługi aplikacji (walidacja syntaktyczna, tx management)
│   └── ports/               # Interfejsy portów (implementowane przez adaptery)
│       ├── input/           # Porty wejściowe (Use Case interfaces)
│       └── output/          # Porty wyjściowe (Repository, EventBus interfaces)
└── domain/                  # Warstwa dziedziny (czyste POJO, zero zależności)
    ├── model/               # Agregaty, Encje, Value Objects
    ├── services/            # Usługi dziedziny (reguły biznesowe)
    └── events/              # Domain Events (LoanCreated, FineIssued, BlockadeApplied)
```

Zasada: **zależności prowadzą wyłącznie do środka** — `domain` nie importuje niczego z warstw zewnętrznych.

---

## Kluczowe przypadki użycia

**PU1 — Wypożyczenie egzemplarza**
Bibliotekarz rejestruje wypożyczenie. System weryfikuje brak blokady, limit 5 książek i dostępność egzemplarza. Automatycznie przypisuje termin zwrotu (30 dni).

**PU2 — Przyjęcie zwrotu egzemplarza**
System porównuje datę zwrotu z terminem. W przypadku opóźnienia automatycznie nalicza karę finansową (`Fine`), nakłada blokadę (`Blockade`) i publikuje zdarzenia do Payment i Notification Context.

**PU3 — Przeglądanie katalogu**
Użytkownik przeszukuje katalog. System odpytuje Catalog Context i synchronicznie pobiera informację o dostępności z Lending Context.

**PU4 — Opłacenie kary**
Czytelnik lub bibliotekarz inicjuje płatność. Po potwierdzeniu przez Payment Context system pomniejsza saldo i automatycznie zdejmuje blokadę, gdy saldo osiągnie 0.

**PU5 — Zarządzanie kontami czytelników**
Bibliotekarz tworzy konto. Identity Context emituje zdarzenie `AccountCreated`, na które reaguje Lending Context, inicjując struktury dla nowego czytelnika.

**PU6 — Zarządzanie katalogiem**
Bibliotekarz dodaje książkę lub egzemplarz. Catalog Context emituje zdarzenie `BookCopyAdded`; Lending Context inicjuje status egzemplarza jako `AVAILABLE`.

---

## Diagram komponentów i diagram klas

Diagramy UML (PlantUML) znajdują się w katalogu `docs/diagrams/`:

```
docs/
└── diagrams/
    ├── component-diagram.puml     # Architektura komponentów SOA + Hexagonal
    └── class-diagram.puml         # Model dziedziny wszystkich Bounded Contexts
```

Wizualizacje zostały przygotowane zgodnie z UML 2.5 przy użyciu notacji PlantUML.

---

## Technologie

| Warstwa | Technologia |
|---|---|
| Język | Java 17 |
| Persystencja |  PostgreSQL |
| Komunikacja asynchroniczna | Rabbit MQ (Domain Events) |
| Testy | JUnit 5, ArchUnit (testy architektury), Testcontainers |
| Build | Maven |

---

## Testy architektury

Projekt zawiera testy `ArchUnit` weryfikujące czystość architektury:

Sprawdzane zasady:
- Warstwa `domain` nie ma zależności do `application` ani `infrastructure`.
- Warstwa `application` nie ma zależności do `infrastructure`.
- Adaptery nie implementują logiki biznesowej.
- Domain Events nie są emitowane z adapterów.

---

## Autorzy i źródła wiedzy

Projekt akademicki zrealizowany na podstawie:

- E. Evans, *Domain-Driven Design. Zapanuj nad złożonym systemem informatycznym*, Helion, 2015
- V. Vernon, *DDD dla architektów oprogramowania*, Helion, 2016
- P. Sobolewski, [*Context Map Patterns w Domain-Driven Design*](https://blog.przemyslawsobolewski.com/context-map-patterns-w-domain-driven-design/), 2025
- dr inż. P. Głuchowski, *Modelowanie i Analiza Systemów Informatycznych — DDD w modelowaniu systemu informatycznego*, Politechnika Wrocławska, 2026
- T. Hombergs, *Nie bój się ubrudzić rąk, tworząc czystą architekturę*, Helion

---

## Licencja

MIT License — szczegóły w pliku `LICENSE`.
