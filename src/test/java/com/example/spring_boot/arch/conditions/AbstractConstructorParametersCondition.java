package com.example.spring_boot.arch.conditions;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaParameter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.List;
import java.util.Set;

// Custom Condition: only allow abstract parameter types in constructors
public class AbstractConstructorParametersCondition extends ArchCondition<JavaConstructor> {

    public AbstractConstructorParametersCondition() {
        super("use only abstract types as parameters");
    }

    @Override
    public void check(JavaConstructor constructor, ConditionEvents events) {
        System.out.println(constructor.getFullName());
        List<JavaParameter> parameters = constructor.getParameters();
        for (JavaParameter parameter : parameters) {
            JavaClass parameterType = parameter.getRawType();

            if (constructor.getFullName().startsWith(parameterType.getFullName())) {
                continue;
            }

            Set<JavaModifier> modifiers = parameterType.getModifiers();
            boolean isAbstract = modifiers.contains(JavaModifier.ABSTRACT);
            boolean isInterface = parameterType.isInterface();
            if (!isInterface && !isAbstract) {
                String message = String.format("Constructor %s in class %s uses non-abstract type parameter %s",
                        constructor.getFullName(),
                        constructor.getOwner().getName(),
                        parameterType.getName());
                events.add(SimpleConditionEvent.violated(constructor, message));
            }
        }
    }
}

