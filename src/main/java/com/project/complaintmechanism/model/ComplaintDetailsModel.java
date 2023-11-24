package com.project.complaintmechanism.model;

import com.project.complaintmechanism.entity.ComplaintTitle;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintDetailsModel {

    private long id;

    @Pattern(regexp = "^.{0,700}$", message = "Please enter less than 700 characters")
    private String remark;

    private int acceptedStatus;

    private boolean solvedStatus;

    private Set<ComplaintTitle> complaintTitleSet;

}
