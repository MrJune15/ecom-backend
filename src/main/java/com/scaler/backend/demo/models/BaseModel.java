package com.scaler.backend.demo.models;


import jakarta.persistence.*;
import java.util.UUID;

@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, columnDefinition = "binary(16)")
    private UUID id;
}
