package com.project.complaintmechanism.customValidator;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.CustomUserDetails;
import com.project.complaintmechanism.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheckValidator implements ConstraintValidator<PasswordCheck, String> {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isValid(String oldPassword, ConstraintValidatorContext constraintValidatorContext) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(customUserDetails.getUsername());
        return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    }

}
