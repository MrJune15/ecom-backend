package com.scaler.backend.demo.services.product;


import com.scaler.backend.demo.dtos.GenericCategoryDTO;
import com.scaler.backend.demo.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FakeCategoryService implements CategoryService {

    @NonNull
    private RestTemplateBuilder restTemplateBuilder;

    private final String categoriesURL = "https://fakestoreapi.com/products/categories";

    @SneakyThrows
    @Override
    public List<GenericCategoryDTO> getAllCategories() {
        ResponseEntity<String[]> responseEntity = restTemplateBuilder.build().getForEntity(categoriesURL, String[].class);
        if (responseEntity.getBody() == null) {
            throw new NotFoundException("No categories available");
        }
        List<GenericCategoryDTO> list = new ArrayList<>();
        for (String category : responseEntity.getBody()) {
            list.add(new GenericCategoryDTO(category));
        }
        return list;
    }
}
