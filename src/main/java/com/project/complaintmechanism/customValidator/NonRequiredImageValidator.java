package com.project.complaintmechanism.customValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class NonRequiredImageValidator implements ConstraintValidator<NonRequiredImage, MultipartFile>  {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        String contentType = multipartFile.getContentType();
        long maxFileSize = 5 * 1024 * 1024;

        if(multipartFile.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("ကျေးဇူးပြု၍ ဓာတ်ပုံတစ်ခုခုကို ရွေးခြယ်ပေးပါ။")
                    .addConstraintViolation();
            return true;
        }

        if(multipartFile.getSize() > maxFileSize) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("ကျေးဇူးပြု၍ 5MB အရွယ်အစားအောက်ရှိသောဓာတ်ပုံကိုသာ ရွေးခြယ်ပေးပါ။")
                    .addConstraintViolation();
            return false;
        }

        if(contentType.equalsIgnoreCase("image/png") || contentType.equalsIgnoreCase("image/jpg") || contentType.equalsIgnoreCase("image/jpeg")) {
            return true;
        }
        else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("ကျေးဇူးပြု၍ JPG (သို့) PNG အမျိုးအစားဖြစ်သောဓာတ်ပုံကိုသာ ရွေးခြယ်ပေးပါ။")
                    .addConstraintViolation();
            return false;
        }

    }

}
