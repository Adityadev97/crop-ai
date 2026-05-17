package com.cross.procrop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "disease_scans")
@Data
@NoArgsConstructor
public class DiseaseScan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @Column(length = 1000)
    private String imageUrl;

    private String detectedDisease;
    
    private Double confidenceScore;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    private LocalDateTime scanDate = LocalDateTime.now();
}
