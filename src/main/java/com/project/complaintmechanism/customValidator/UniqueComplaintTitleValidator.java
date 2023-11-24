package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.service.ComplaintTitleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueComplaintTitleValidator implements ConstraintValidator<UniqueComplaintTitle, String> {

    @Autowired
    private ComplaintTitleService complaintTitleService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !complaintTitleService.existsByName(name);
    }

}
