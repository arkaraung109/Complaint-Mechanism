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
    private IndustrialZoneService industrialZoneService;

    @Override
    public boolean isValid(IndustrialZoneModel industrialZoneModel, ConstraintValidatorContext constraintValidatorContext) {
        long cityId = industrialZoneModel.getCityId();
        long townshipId = industrialZoneModel.getTownshipId();
        String industrialZoneName = industrialZoneModel.getName();
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                                  .addPropertyNode( "name" )
                                  .addConstraintViolation();
        return !industrialZoneService.findExistsByCityIdAndTownshipIdAndIndustrialZoneName(cityId, townshipId, industrialZoneName);
    }

}
