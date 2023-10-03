package com.scaler.backend.demo.controllers;


import com.scaler.backend.demo.dtos.GenericCategoryDTO;
import com.scaler.backend.demo.services.product.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService service;

    @GetMapping()
    public List<GenericCategoryDTO> getAllCategories() {
        return service.getAllCategories();
    }
}
