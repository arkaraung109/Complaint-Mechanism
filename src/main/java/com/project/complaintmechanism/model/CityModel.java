package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueCity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityModel {

    private long id;

    @NotBlank(message = "Please enter city name")
    @Pattern.List({
            @Pattern(regexp = "^([a-zA-Z\\s<>~`!\\[\\]|@#^*+=:;/?%$\"\\\\]+)|(\\s*)$", message = "Please enter starting with alphabet"),
            @Pattern(regexp = "^([^<>~`!\\[\\]|@#^*+=:;/?%$\"\\\\]|[0-9]){0,100}$", message = "Please enter excluding special characters")
    })
    @UniqueCity(message = "City already exists")
    private String name;

}
