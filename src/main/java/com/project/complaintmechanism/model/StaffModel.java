package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.PasswordMatch;
import com.project.complaintmechanism.customValidator.UniqueUser;
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
@PasswordMatch(message = "Password does not match with confirm password")
public class StaffModel {

    private long id;

    @NotBlank(message = "Please enter staff name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    @NotBlank(message = "Please enter email")
    @Pattern(regexp = "^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})|(\\s*)$", message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Please enter phone")
    @Pattern(regexp = "^(09-[0-9]{7,9})|(09\\s*[0-9]{7,9})|(\\s*)$", message = "Please enter valid phone")
    private String phone;

    @NotBlank(message = "Please enter username")
    @UniqueUser(message = "User already exists")
    private String username;

    @NotBlank(message = "Please enter password")
    @Pattern(regexp = "^.{8,30}|.{0}$", message = "Please enter between 8 characters and 30 characters")
    private String password;

    @NotBlank(message = "Please enter confirm password")
    private String confirmPassword;

}
