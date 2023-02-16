package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueCompany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UniqueCompany(message = "Company already exists")
public class CompanyModel {

    private long id;

    @NotBlank(message = "Please enter company name")
    @Pattern.List({
            @Pattern(regexp = "^([a-zA-Z\\s]+.*$){0,100}", message = "Please enter starting with alphabet"),
            @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]|[0-9]){0,100}$", message = "Please enter excluding special characters")
    })
    private String name;

    @NotBlank(message = "Please choose city name")
    private String cityName;

    @NotBlank(message = "Please choose township name")
    private String townshipName;

    @NotBlank(message = "Please choose industrial zone name")
    private String industrialZoneName;

}
