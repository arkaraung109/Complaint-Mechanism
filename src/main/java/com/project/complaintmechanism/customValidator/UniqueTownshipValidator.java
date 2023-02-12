package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.model.TownshipModel;
import com.project.complaintmechanism.service.TownshipService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueTownshipValidator implements ConstraintValidator<UniqueTownship, TownshipModel> {

    @Autowired
    TownshipService townshipService;

    @Override
    public boolean isValid(TownshipModel townshipModel, ConstraintValidatorContext constraintValidatorContext) {
        String townshipName = townshipModel.getName();
        String cityName = townshipModel.getCityName();
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                                  .addPropertyNode( "name" )
                                  .addConstraintViolation();
        return !townshipService.findExistsByCityName(cityName, townshipName);
    }

}
