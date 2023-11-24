package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.model.TownshipModel;
import com.project.complaintmechanism.service.TownshipService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueTownshipValidator implements ConstraintValidator<UniqueTownship, TownshipModel> {

    @Autowired
    private TownshipService townshipService;

    @Override
    public boolean isValid(TownshipModel townshipModel, ConstraintValidatorContext constraintValidatorContext) {
        long cityId = townshipModel.getCityId();
        String townshipName = townshipModel.getName();
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                                  .addPropertyNode( "name" )
                                  .addConstraintViolation();
        return !townshipService.findExistsByCityIdAndTownshipName(cityId, townshipName);
    }

}
