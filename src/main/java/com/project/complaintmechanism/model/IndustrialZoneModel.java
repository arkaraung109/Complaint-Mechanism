package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueIndustrialZone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UniqueIndustrialZone(message = "Industrial zone already exists")
public class IndustrialZoneModel {

    private long id;

    @NotBlank(message = "Please enter industrial zone name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    private String name;

    @Positive(message = "Please choose city")
    private long cityId;

    @Positive(message = "Please choose township")
    private long townshipId;

}
