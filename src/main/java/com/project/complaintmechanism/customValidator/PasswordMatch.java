package com.project.complaintmechanism.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {PasswordMatchValidator.class})
@Retention(RUNTIME)
@Target({TYPE})
public @interface PasswordMatch {

    public String message() default "Password does not match with confirm password!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}
