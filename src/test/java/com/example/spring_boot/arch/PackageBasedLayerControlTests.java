package com.example.spring_boot.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/*
Assumptions
Controller layer: Can depend only on the service layer.
Service layer: Can depend on repositories and other services, but not on controllers.
Repository layer: Directly interacts with the data source and should only be depended on by services, not controllers or other repositories.
Component layer: Generic and can be used across any layer.
 */

/*
These layer usage tests are expressed as requiring classes with specific annotations to be placed in specifically named packages
 */

public class PackageBasedLayerControlTests {
    private final JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.example.spring_boot");

    /*
    The controller should not depend on things annotated as @Service or @Repository
     */
    @Test
    public void svc_may_not_depend_on_ctrl_layer() {
        ArchRule rule = noClasses().that()
                .areAnnotatedWith(Service.class)
                .or()
                .areAnnotatedWith(Repository.class)
                .should()
                .dependOnClassesThat()
                .areAnnotatedWith(Controller.class);

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * Controllers should only depend on services and components, not on repositories or other controllers.
     */
    @Test
    public void controllers_should_only_depend_on_services_and_components() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Controller.class)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..service..", "java..", "..component..", "..springframework..");

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * Services should only depend on repositories, other services, and components.
     */
    @Test
    public void services_should_only_depend_on_repositories_services_and_components() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Service.class)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..repository..", "..service..", "..component..", "java..", "..springframework..");

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * Repositories should only depend on domain, JPA, or component classes, not on services, controllers, or other repositories.
     */
    @Test
    public void repositories_should_only_depend_on_domain_or_components() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("java..", "..component..", "..domain..", "..repository..", "..springframework..");

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * No layer should depend on controllers, except other controllers.
     */
    @Test
    public void no_layer_should_depend_on_controller_layer() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAnnotatedWith(Component.class)
                .and().areNotAnnotatedWith(Controller.class)
                .should().dependOnClassesThat()
                .areAnnotatedWith(Controller.class);

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * Only repositories should directly access the data source.
     */
    @Test
    public void only_repositories_should_access_data_source() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().dependOnClassesThat().resideInAnyPackage("..persistence..", "java..");

        rule.allowEmptyShould(true).check(importedClasses);
    }
}
