package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.model.StaffModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, StaffModel> {

    @Override
    public boolean isValid(StaffModel staffModel, ConstraintValidatorContext constraintValidatorContext) {
        String password = staffModel.getPassword();
        String confirmPassword = staffModel.getConfirmPassword();
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                .addPropertyNode( "confirmPassword" )
                .addConstraintViolation();
        return password != null && password.equals(confirmPassword);
    }

}
