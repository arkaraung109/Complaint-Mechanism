package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueCompany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UniqueCompany(message = "Company already exists")
public class CompanyModel {

    private long id;

    @NotBlank(message = "Please enter company name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    @NotBlank(message = "Please choose city name")
    private String cityName;

    @NotBlank(message = "Please choose township name")
    private String townshipName;

    @NotBlank(message = "Please choose industrial zone name")
    private String industrialZoneName;

}
