package com.project.complaintmechanism.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {UniqueUserValidator.class})
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface UniqueUser {

    public String message() default "Username already exists!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}
