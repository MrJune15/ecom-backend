package com.scaler.backend.demo.services.product;

import com.scaler.backend.demo.dtos.GenericProductDTO;
import com.scaler.backend.demo.exceptions.NotFoundException;

import java.util.List;

public interface ProductService {
    GenericProductDTO getProductById(Long id) throws NotFoundException;

    GenericProductDTO createProduct(GenericProductDTO payload) throws NotFoundException;

    GenericProductDTO deleteProductById(Long id) throws NotFoundException;

    GenericProductDTO updateProductById(Long id, GenericProductDTO payload) throws NotFoundException;

    List<GenericProductDTO> getProducts() throws NotFoundException;

    List<GenericProductDTO> getProductsByCategory(String category) throws NotFoundException;
}
