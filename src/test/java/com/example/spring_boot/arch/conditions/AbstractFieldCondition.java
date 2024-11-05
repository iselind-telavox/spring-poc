package com.example.spring_boot.arch.conditions;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class AbstractFieldCondition extends ArchCondition<JavaField> {

    public AbstractFieldCondition() {
        super("use only abstract types as their types");
    }

    @Override
    public void check(JavaField javaField, ConditionEvents events) {
        System.out.println(javaField.getFullName());
        JavaClass fieldType = javaField.getRawType();

        if (fieldType.isPrimitive()) {
            return;
        }
        if (fieldType.getFullName().startsWith("java.lang")) {
            return;
        }
        if (javaField.getFullName().startsWith(fieldType.getFullName())) {
            return;
        }

        boolean isAbstract = fieldType.getModifiers().contains(JavaModifier.ABSTRACT);
        boolean isInterface = fieldType.isInterface();
        if (!isAbstract && !isInterface) {
            String message = String.format("field %s use non-abstract type %s",
                    javaField.getFullName(),
                    fieldType.getFullName());
            events.add(SimpleConditionEvent.violated(javaField, message));
        }
    }
}
