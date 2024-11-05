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

/*
These tests are expressed in terms of the annotations only. No package names are considered
 */
public class AnnotationBasedLayerControlTests {
    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.example.spring_boot");

    /**
     * Controllers should only depend on services or components, not on repositories or other controllers.
     */
    @Test
    public void controllers_should_only_depend_on_services_and_components() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Controller.class)
                .should().onlyDependOnClassesThat()
                .areAnnotatedWith(Service.class)
                .orShould().onlyDependOnClassesThat()
                .areAnnotatedWith(Component.class)
                .orShould().onlyDependOnClassesThat()
                .areAnnotatedWith(Controller.class)
                .orShould().dependOnClassesThat()
                .resideInAnyPackage("java..");

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
                .areAnnotatedWith(Repository.class)
                .orShould().onlyDependOnClassesThat()
                .areAnnotatedWith(Service.class)
                .orShould().onlyDependOnClassesThat()
                .areAnnotatedWith(Component.class)
                .orShould().dependOnClassesThat()
                .resideInAnyPackage("java..");

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * Repositories should only depend on components and domain objects (no direct dependencies on services or controllers).
     */
    @Test
    public void repositories_should_only_depend_on_components() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().onlyDependOnClassesThat()
                .areAnnotatedWith(Component.class)
                .orShould().onlyDependOnClassesThat()
                .areNotAnnotatedWith(Service.class)
                .andShould().onlyDependOnClassesThat()
                .areNotAnnotatedWith(Controller.class);

        rule.allowEmptyShould(true).check(importedClasses);
    }

    /**
     * No layer (except other controllers) should depend on controllers.
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
     * Only repositories should access the data source directly.
     */
    @Test
    public void only_repositories_should_access_data_source() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().accessClassesThat()
                .resideInAnyPackage("..persistence..", "..repository..");

        rule.allowEmptyShould(true).check(importedClasses);
    }
}
