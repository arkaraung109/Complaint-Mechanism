package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.service.TownshipService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueTownshipNameValidator implements ConstraintValidator<UniqueTownshipName, String> {
    @Autowired
    TownshipService townshipService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !townshipService.existsByName(s);
    }
}
