package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueTownship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueTownship(message = "Township already exists")
public class TownshipModel {

    private long id;

    @NotBlank(message = "Please enter township name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    @Positive(message = "Please choose city")
    private long cityId;

}
