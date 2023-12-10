package com.project.complaintmechanism.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "complaint_title")
public class ComplaintTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "complaintTitleSet", fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE
    })
    @JsonBackReference
    private Set<Complaint> complaintSet = new HashSet<>();

}
