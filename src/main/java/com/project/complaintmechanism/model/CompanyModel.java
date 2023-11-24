package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueCompany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueCompany(message = "Company already exists")
public class CompanyModel {

    private long id;

    @NotBlank(message = "Please enter company name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    private boolean activeStatus;

    @Positive(message = "Please choose city")
    private long cityId;

    @Positive(message = "Please choose township")
    private long townshipId;

    @Positive(message = "Please choose industrial zone")
    private long industrialZoneId;

}
