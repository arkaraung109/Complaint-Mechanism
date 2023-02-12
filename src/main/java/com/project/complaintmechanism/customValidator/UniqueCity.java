package com.project.complaintmechanism.customValidator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UniqueCityValidator.class})
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface UniqueCity {

    public String message() default "Name already exists!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}
