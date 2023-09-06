package com.scaler.backend.demo.controllers;

import com.scaler.backend.demo.dtos.GenericProductDTO;
import com.scaler.backend.demo.services.product.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @NonNull
    private ProductService productService;


    @GetMapping("{id}")
    public GenericProductDTO getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<GenericProductDTO> getProductS() {
        return productService.getProducts();
    }

    @PostMapping
    public GenericProductDTO createProduct(@RequestBody GenericProductDTO body) {
        return productService.createProduct(body);
    }

    @DeleteMapping("{id}")
    public GenericProductDTO deleteProductById(@PathVariable("id") Long id) {
        return productService.deleteProductById(id);
    }
}
