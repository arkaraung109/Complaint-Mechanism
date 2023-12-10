package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.PasswordCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordModel {

    @NotBlank(message = "Please enter old password")
    @PasswordCheck(message = "Old password is incorrect")
    private String oldPassword;

    @NotBlank(message = "Please enter new password")
    @Pattern(regexp = "^.{8,30}|.{0}$", message = "Please enter between 8 characters and 30 characters")
    private String newPassword;

}
