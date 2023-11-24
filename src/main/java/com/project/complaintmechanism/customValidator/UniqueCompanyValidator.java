package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.model.CompanyModel;
import com.project.complaintmechanism.service.CompanyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueCompanyValidator implements ConstraintValidator<UniqueCompany, CompanyModel> {

    @Autowired
    private CompanyService companyService;

    @Override
    public boolean isValid(CompanyModel companyModel, ConstraintValidatorContext constraintValidatorContext) {
        long cityId = companyModel.getCityId();
        long townshipId = companyModel.getTownshipId();
        long industrialZoneId = companyModel.getIndustrialZoneId();
        String companyName = companyModel.getName();

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                                  .addPropertyNode( "name" )
                                  .addConstraintViolation();
        return !companyService.findExistsByCityIdAndTownshipIdAndIndustrialZoneIdAndCompanyName(cityId, townshipId, industrialZoneId, companyName);
    }

}
