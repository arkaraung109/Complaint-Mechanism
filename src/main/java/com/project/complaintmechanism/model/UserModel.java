package com.project.complaintmechanism.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private long id;

    @NotBlank(message = "Please enter username")
    private String username;

    @NotBlank(message = "Please enter password")
    private String password;

}
