package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueTownship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UniqueTownship(message = "Township already exists")
public class TownshipModel {

    private long id;

    @NotBlank(message = "Please enter township name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    @NotBlank(message = "Please choose city name")
    private String cityName;

}
