package com.project.complaintmechanism.customValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {RequiredImageValidator.class})
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface RequiredImage {

    public String message() default "Only JPG and PNG images are allowed!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}
