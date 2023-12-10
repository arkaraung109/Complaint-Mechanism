package com.project.complaintmechanism.model;

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
public class ProfileModel {

    private long id;

    @NotBlank(message = "Please enter name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    @NotBlank(message = "Please enter email")
    @Pattern(regexp = "^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})|(\\s*)$", message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Please enter phone")
    @Pattern(regexp = "^(09-[0-9]{7,9})|(09\\s*[0-9]{7,9})|(\\s*)$", message = "Please enter valid phone")
    private String phone;

    private String username;

}
