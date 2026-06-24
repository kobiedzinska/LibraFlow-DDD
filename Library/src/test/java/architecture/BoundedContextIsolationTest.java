package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * Context-to-context isolation rules (Context Map enforcement).
 *
 * Each bounded context (Loan, Catalog, Payments, Notifications, ReaderAccounts)
 * owns its model. Cross-context coupling at the domain level is forbidden:
 * a context's *domain* may never depend on another context's classes. Integration
 * happens only through infrastructure adapters and domain events, following the
 * Context Map patterns (Open Host Service, Customer/Supplier, Anti-Corruption Layer)
 * described in the project README.
 */
@DisplayName("Bounded context isolation (Context Map)")
class BoundedContextIsolationTest {

    private static final String LOAN = "Loan..";
    private static final String CATALOG = "Catalog..";
    private static final String PAYMENTS = "Payments..";
    private static final String NOTIFICATIONS = "Notifications..";
    private static final String READERS = "ReaderAccounts..";

    private static JavaClasses classes;

    @BeforeAll
    static void importClasses() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("Loan", "Catalog", "Payments", "Notifications", "ReaderAccounts");
    }

    @Test
    @DisplayName("there are no cyclic dependencies between bounded-context domains")
    void contextDomainsAreFreeOfCycles() {
        // Cycles are checked at the DOMAIN level only. Infrastructure adapters
        // legitimately wire contexts together (e.g. via the SharedKernel EventBus
        // and event handlers), so context-level slices that include infrastructure
        // would report expected, intentional cycles.
        ArchRule rule = slices()
                .matching("(*).application.domain..")
                .namingSlices("Domain of $1")
                .should().beFreeOfCycles();
        rule.check(classes);
    }

    @Test
    @DisplayName("the Lending domain must not depend on any other context")
    void lendingDomainIsIsolated() {
        noClasses()
                .that().resideInAPackage("Loan.application.domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(CATALOG, PAYMENTS, NOTIFICATIONS, READERS)
                .check(classes);
    }

    @Test
    @DisplayName("the Catalog domain must not depend on any other context")
    void catalogDomainIsIsolated() {
        noClasses()
                .that().resideInAPackage("Catalog.application.domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(LOAN, PAYMENTS, NOTIFICATIONS, READERS)
                .check(classes);
    }

    @Test
    @DisplayName("the Payments domain must not depend on any other context")
    void paymentsDomainIsIsolated() {
        noClasses()
                .that().resideInAPackage("Payments.application.domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(LOAN, CATALOG, NOTIFICATIONS, READERS)
                .check(classes);
    }

    @Test
    @DisplayName("the Notifications domain must not depend on any other context")
    void notificationsDomainIsIsolated() {
        noClasses()
                .that().resideInAPackage("Notifications.application.domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(LOAN, CATALOG, PAYMENTS, READERS)
                .check(classes);
    }

    @Test
    @DisplayName("the ReaderAccounts domain must not depend on any other context")
    void readerAccountsDomainIsIsolated() {
        noClasses()
                .that().resideInAPackage("ReaderAccounts.application.domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(LOAN, CATALOG, PAYMENTS, NOTIFICATIONS)
                .check(classes);
    }

    @Test
    @DisplayName("the Lending application service must not bind to another context's domain model")
    void lendingApplicationStaysWithinItsContext() {
        noClasses()
                .that().resideInAPackage("Loan.application.service..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "Catalog.application.domain..",
                        "Payments.application.domain..",
                        "Notifications.application.domain..",
                        "ReaderAccounts.application.domain..")
                .check(classes);
    }
}
