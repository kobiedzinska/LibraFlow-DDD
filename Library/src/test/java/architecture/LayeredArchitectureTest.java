package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * Hexagonal / Clean Architecture rules applied within every bounded context.
 *
 * Per-context package layout:
 *   {Context}.application.domain..      -> domain layer (model + events + domain services)
 *   {Context}.application.ports..       -> ports (input/output interfaces)
 *   {Context}.application.port..        -> ports (Loan context spelling variant)
 *   {Context}.application.service..     -> application services (use cases)
 *   {Context}.infrastructure..          -> adapters (REST, JPA, messaging, HTTP)
 *
 * Core principle: dependencies point inward, toward the domain.
 */
@DisplayName("Layered architecture (Hexagonal / Clean) — dependencies point inward")
class LayeredArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void importClasses() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("Loan", "Catalog", "Payments", "Notifications", "ReaderAccounts");
    }

    @Test
    @DisplayName("the domain layer must not depend on application services")
    void domainDoesNotDependOnApplicationServices() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application.domain..")
                .should().dependOnClassesThat().resideInAPackage("..application.service..");
        rule.check(classes);
    }

    @Test
    @DisplayName("the domain layer must not depend on infrastructure")
    void domainDoesNotDependOnInfrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application.domain..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");
        rule.check(classes);
    }

    @Test
    @DisplayName("the application layer must not depend on infrastructure")
    void applicationDoesNotDependOnInfrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");
        rule.check(classes);
    }

    @Test
    @DisplayName("ports must not depend on infrastructure")
    void portsDoNotDependOnInfrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..application.ports..", "..application.port..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");
        rule.check(classes);
    }

    @Test
    @DisplayName("the domain layer must not use persistence, messaging or web frameworks")
    void domainStaysFrameworkFree() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application.domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "javax.persistence..",
                        "jakarta.persistence..",
                        "com.rabbitmq..",
                        "java.sql..",
                        "com.zaxxer.hikari..");
        rule.check(classes);
    }

    @Test
    @DisplayName("the Lending context honours a strict layered architecture")
    void lendingContextIsLayered() {
        ArchRule rule = layeredArchitecture().consideringOnlyDependenciesInLayers()
                .layer("Domain").definedBy("Loan.application.domain..")
                .layer("Ports").definedBy("Loan.application.port..")
                .layer("Application").definedBy("Loan.application.service..")
                .layer("Infrastructure").definedBy("Loan.infrastructure..")

                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Ports", "Infrastructure");
        rule.check(classes);
    }
}
