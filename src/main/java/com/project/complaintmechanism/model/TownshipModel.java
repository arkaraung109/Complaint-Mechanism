package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueTownshipName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TownshipModel {

    private long id;

    @NotBlank(message = "Please type township name")
    @Pattern(regexp = "^([^<>~`'!|@#^*+=:;/?%$]|[0-9]){0,100}$", message = "Please type excluding special characters")
    @UniqueTownshipName(message = "Township Name already exists")
    private String name;

    @NotBlank(message = "Please choose city name")
    private String cityName;

}
