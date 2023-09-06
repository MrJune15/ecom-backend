package com.scaler.backend.demo.controllers;

import com.scaler.backend.demo.dtos.GenericProductDTO;
import com.scaler.backend.demo.exceptions.NotFoundException;
import com.scaler.backend.demo.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("{id}")
    public GenericProductDTO getProductById(@PathVariable("id") Long id) throws NotFoundException {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<GenericProductDTO> getProductS() throws NotFoundException {
        return productService.getProducts();
    }

    @PutMapping("{id}")
    public GenericProductDTO updateProduct(@PathVariable("id") Long id, @RequestBody GenericProductDTO body) throws NotFoundException {
        return productService.updateProductById(id, body);
    }

    @PostMapping
    public GenericProductDTO createProduct(@RequestBody GenericProductDTO body) throws NotFoundException {
        return productService.createProduct(body);
    }

    @DeleteMapping("{id}")
    public GenericProductDTO deleteProductById(@PathVariable("id") Long id) throws NotFoundException {
        return productService.deleteProductById(id);
    }
}
