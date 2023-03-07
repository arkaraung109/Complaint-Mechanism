package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.UniqueComplaintTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintTitleModel {

    private long id;

    @NotBlank(message = "Please enter complaint title name")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "Please enter excluding special characters")
    @UniqueComplaintTitle(message = "Complaint title already exists")
    private String name;

}
