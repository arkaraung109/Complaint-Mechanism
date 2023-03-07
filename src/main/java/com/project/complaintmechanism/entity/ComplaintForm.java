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

    @Column(length = 700)
    private String description;

    private String remark;

    private LocalDateTime submittedAt;

    private boolean readStatus;

    private int acceptedStatus;

    private boolean solvedStatus;

    private boolean tempDeletedStatus;

    private String name;

    private String gender;

    private String phone;

    private String email;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] idCardFront;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] idCardBack;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] ecPhoto1;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] ecPhoto2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "complaint_form_title", joinColumns = @JoinColumn(name = "complaint_form_id"), inverseJoinColumns = @JoinColumn(name = "complaint_title_id"))
    @JsonManagedReference
    private Set<ComplaintTitle> complaintTitleSet = new HashSet<>();

}
