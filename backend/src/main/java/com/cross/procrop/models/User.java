package com.cross.procrop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role; // FARMER, ADMIN

    private Double locationLat;
    private Double locationLng;
    
    private String language = "en"; // en, hi
}
