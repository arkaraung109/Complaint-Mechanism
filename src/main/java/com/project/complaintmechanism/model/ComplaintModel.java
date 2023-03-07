package com.project.complaintmechanism.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintModel {

    private long id;

    private String description;

    private String remark;

    private LocalDate date;

    private String readStatus;

    private String acceptedStatus;

    private String solvedStatus;

    private boolean tempDeletedStatus;

    private String name;

    private String gender;

    private String phone;

    private String email;

    private String companyName;

    private String complaintTitle;

}
