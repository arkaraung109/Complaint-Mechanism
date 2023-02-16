package com.project.complaintmechanism.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private String remark;

    private LocalDateTime submittedAt;

    private boolean readStatus;

    private boolean acceptedStatus;

    private boolean solvedStatus;

    private String name;

    private String gender;

    private String phone;

    private String email;

    private String idCardFront;

    private String idCardBack;

    private String ecPhoto1;

    private String ecPhoto2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "complaint_form_title", joinColumns = @JoinColumn(name = "complaint_form_id"), inverseJoinColumns = @JoinColumn(name = "complaint_title_id"))
    @JsonManagedReference
    private Set<ComplaintTitle> complaintTitleSet = new HashSet<>();

}
