package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * DDD tactical / hexagonal naming and placement conventions.
 */
@DisplayName("DDD tactical conventions and port/adapter placement")
class DddConventionsTest {

    private static JavaClasses classes;

    @BeforeAll
    static void importClasses() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("Loan", "Catalog", "Payments", "Notifications", "ReaderAccounts");
    }

    @Test
    @DisplayName("ports named I* are interfaces")
    void portsAreInterfaces() {
        classes()
                .that().resideInAnyPackage("..application.ports..", "..application.port..")
                .and().haveSimpleNameStartingWith("I")
                .should().beInterfaces()
                .check(classes);
    }

    @Test
    @DisplayName("controllers live only in the inbound infrastructure layer")
    void controllersAreInboundAdapters() {
        classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("..infrastructure.in..")
                .check(classes);
    }

    @Test
    @DisplayName("domain events reside in the domain layer")
    void domainEventsAreInDomain() {
        // Context-specific events live in each context's domain. The shared
        // SharedKernel.DomainEvent marker interface is exempt by design.
        classes()
                .that().haveSimpleNameEndingWith("Event")
                .and().resideOutsideOfPackage("SharedKernel..")
                .should().resideInAPackage("..application.domain..")
                .check(classes);
    }

    @Test
    @DisplayName("domain services must not depend on input ports (use cases are application services)")
    void domainServicesAreNotUseCases() {
        noClasses()
                .that().resideInAPackage("..application.domain.service..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..application.ports.in..", "..application.port.in..")
                .check(classes);
    }

    @Test
    @DisplayName("application services must not depend on concrete infrastructure adapters")
    void useCasesDependOnPortsNotAdapters() {
        noClasses()
                .that().resideInAPackage("..application.service..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
                .check(classes);
    }
}
