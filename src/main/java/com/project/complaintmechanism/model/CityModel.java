package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueCity;
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
public class CityModel {

    private long id;

    @NotBlank(message = "Please enter city name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    @UniqueCity(message = "City already exists")
    private String name;

}
