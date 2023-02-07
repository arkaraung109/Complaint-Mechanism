package com.project.complaintmechanism.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "complaintTitleSet", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<ComplaintForm> complaintFormSet = new HashSet<>();

}
