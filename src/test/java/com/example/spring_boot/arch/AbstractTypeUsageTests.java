package com.example.spring_boot.arch;

import com.example.spring_boot.arch.conditions.AbstractConstructorParametersCondition;
import com.example.spring_boot.arch.conditions.AbstractFieldCondition;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractTypeUsageTests {
    // Import your package here
    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.example.spring_boot");

    @Test
    public void constructorsShouldOnlyUseAbstractTypes() {

        AbstractConstructorParametersCondition useAbstractParametersInConstructors = new AbstractConstructorParametersCondition();

        // Define the rule: constructors should only use abstract types as parameters
        ArchRule constructorsShouldOnlyUseAbstractTypes = ArchRuleDefinition.constructors()
                .should(useAbstractParametersInConstructors);

        // Check the rule
        constructorsShouldOnlyUseAbstractTypes.allowEmptyShould(true).check(importedClasses);
    }

    @Test
    public void fieldsShouldBeAbstractOrPrimitive() {
        AbstractFieldCondition useAbstractFieldTypes = new AbstractFieldCondition();
        ArchRule rule = ArchRuleDefinition.fields().should(useAbstractFieldTypes);

        rule.allowEmptyShould(true).check(importedClasses);
    }

    @Test
    public void autoWiredFieldsShouldBeAbstractOrPrimitive() {
        AbstractFieldCondition useAbstractFieldTypes = new AbstractFieldCondition();
        ArchRule rule = ArchRuleDefinition.fields().that()
                .areAnnotatedWith(Autowired.class)
                .should(useAbstractFieldTypes);

        rule.allowEmptyShould(true).check(importedClasses);
    }

}
