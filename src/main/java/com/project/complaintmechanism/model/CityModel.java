package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueCityName;
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
    @NotBlank(message = "Please type city name")
    @Pattern(regexp = "^([^<>~`'!|@#^*+=:;/?%$]|[0-9]){0,100}$", message = "Please type excluding special characters")
    @UniqueCityName(message = "City Name already exists")
    private String name;
}
