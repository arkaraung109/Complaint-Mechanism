package com.project.complaintmechanism.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {NonRequiredImageValidator.class})
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface NonRequiredImage {

    public String message() default "Please choose an image";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}
