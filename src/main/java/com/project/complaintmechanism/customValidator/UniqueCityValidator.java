package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.service.CityService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueCityValidator implements ConstraintValidator<UniqueCity, String> {

    @Autowired
    private CityService cityService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !cityService.existsByName(name);
    }

}
