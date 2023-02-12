package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueIndustrialZone;
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
@UniqueIndustrialZone(message = "Industrial zone already exists")
public class IndustrialZoneModel {

    private long id;

    @NotBlank(message = "Please enter industrial zone name")
    @Pattern.List({
            @Pattern(regexp = "^([a-zA-Z\\s<>~`!\\[\\]|@#^*+=:;/?%$\"\\\\]+)|(\\s*)$", message = "Please enter starting with alphabet"),
            @Pattern(regexp = "^([^<>~`!\\[\\]|@#^*+=:;/?%$\"\\\\]|[0-9]){0,100}$", message = "Please enter excluding special characters")
    })
    private String name;

    @NotBlank(message = "Please choose city name")
    private String cityName;

    @NotBlank(message = "Please choose township name")
    private String townshipName;

}
