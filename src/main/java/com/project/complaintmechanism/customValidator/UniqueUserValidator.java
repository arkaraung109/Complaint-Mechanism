package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByUsername(username);
    }

}
