package com.scaler.backend.demo.dtos;

import lombok.Data;

@Data
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}
