package com.scaler.backend.demo.services.product;

import com.scaler.backend.demo.dtos.GenericCategoryDTO;

import java.util.List;

public interface CategoryService {
    List<GenericCategoryDTO> getAllCategories();
}
