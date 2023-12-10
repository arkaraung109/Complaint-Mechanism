package com.project.complaintmechanism.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {PasswordCheckValidator.class})
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface PasswordCheck {

    public String message() default "Password is wrong!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}