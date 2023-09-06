package com.scaler.backend.demo.services.product;

import com.scaler.backend.demo.dtos.GenericProductDTO;
import com.scaler.backend.demo.models.Product;

public interface ProductService {
    GenericProductDTO getProductById(Long id);

    GenericProductDTO createProduct(GenericProductDTO payload);

    GenericProductDTO deleteProductById(Long id);

    GenericProductDTO updateProductById(Long id);
}
