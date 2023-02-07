package com.project.complaintmechanism.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Township {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "city_id")
    @JsonBackReference
    private City city;

    @OneToMany(mappedBy = "township", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<IndustrialZone> industrialZoneList;
}
