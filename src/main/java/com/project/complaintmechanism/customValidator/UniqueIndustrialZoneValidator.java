package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.model.IndustrialZoneModel;
import com.project.complaintmechanism.service.IndustrialZoneService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueIndustrialZoneValidator implements ConstraintValidator<UniqueIndustrialZone, IndustrialZoneModel> {

    @Autowired
    IndustrialZoneService industrialZoneService;

    @Override
    public boolean isValid(IndustrialZoneModel industrialZoneModel, ConstraintValidatorContext constraintValidatorContext) {
        String cityName = industrialZoneModel.getCityName();
        String townshipName = industrialZoneModel.getTownshipName();
        String industrialZoneName = industrialZoneModel.getName();
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                                  .addPropertyNode( "name" )
                                  .addConstraintViolation();
        return !industrialZoneService.findExistsByCityNameAndTownshipName(cityName, townshipName, industrialZoneName);
    }

}
