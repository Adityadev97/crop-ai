package com.cross.procrop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "farms")
@Data
@NoArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    private Double areaSize; // in hectares
    
    private String soilType;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Crop> crops;
}
