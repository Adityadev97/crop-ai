package com.cross.procrop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "crops")
@Data
@NoArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(nullable = false)
    private String cropType;

    private LocalDate plantingDate;

    private String status; // GROWING, HARVESTED, ISSUE

    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL)
    private List<DiseaseScan> scans;
}
