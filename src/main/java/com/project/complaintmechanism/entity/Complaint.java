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
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "description", length = 700)
    private String description;

    @Column(name = "remark")
    private String remark;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "read_status")
    private boolean readStatus;

    @Column(name = "accepted_status")
    private int acceptedStatus;

    @Column(name = "solved_status")
    private boolean solvedStatus;

    @Column(name = "temp_deleted_status")
    private boolean tempDeletedStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "id_card_front", columnDefinition = "MEDIUMBLOB")
    private byte[] idCardFront;

    @Lob
    @Column(name = "id_card_back", columnDefinition = "MEDIUMBLOB")
    private byte[] idCardBack;

    @Lob
    @Column(name = "ec_photo1", columnDefinition = "MEDIUMBLOB")
    private byte[] ecPhoto1;

    @Lob
    @Column(name = "ec_photo2", columnDefinition = "MEDIUMBLOB")
    private byte[] ecPhoto2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE
    })
    @OrderBy("name asc")
    @JoinTable(name = "complaint_has_complaint_title", joinColumns = @JoinColumn(name = "complaint_id"), inverseJoinColumns = @JoinColumn(name = "complaint_title_id"))
    @JsonManagedReference
    private Set<ComplaintTitle> complaintTitleSet = new HashSet<>();

}
