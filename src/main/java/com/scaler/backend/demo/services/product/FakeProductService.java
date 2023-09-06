package com.scaler.backend.demo.services.product;

import com.scaler.backend.demo.dtos.FakeStoreProductDTO;
import com.scaler.backend.demo.dtos.GenericProductDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FakeProductService implements ProductService {

    private final String getProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private final String productsUrl = "https://fakestoreapi.com/products";

    @NonNull
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public GenericProductDTO getProductById(Long id) {
        FakeStoreProductDTO body = restTemplateBuilder.build().getForEntity(getProductRequestUrl, FakeStoreProductDTO.class, id).getBody();
        GenericProductDTO result = new GenericProductDTO();

        if (body != null) {
            result.setImage(body.getImage());
            result.setDescription(body.getDescription());
            result.setTitle(body.getTitle());
            result.setPrice(body.getPrice());
            result.setCategory(body.getCategory());
        }
        return result;
    }

    @Override
    public GenericProductDTO createProduct(GenericProductDTO payload) {
        return restTemplateBuilder
                .build()
                .postForEntity(
                        productsUrl,
                        payload,
                        GenericProductDTO.class
                ).getBody();
    }

    @Override
    public GenericProductDTO deleteProductById(Long id) {
        restTemplateBuilder.build().delete(getProductRequestUrl, id);
        return new GenericProductDTO();
    }

    @Override
    public GenericProductDTO updateProductById(Long id) {
        return null;
    }

    @Override
    public List<GenericProductDTO> getProducts() {
        ResponseEntity<FakeStoreProductDTO[]> response = restTemplateBuilder.build().getForEntity(productsUrl, FakeStoreProductDTO[].class);
        List<GenericProductDTO> list = new ArrayList<>();

        if (response.getBody() != null) {
            for (FakeStoreProductDTO each : response.getBody()) {
                GenericProductDTO result = new GenericProductDTO();
                result.setImage(each.getImage());
                result.setDescription(each.getDescription());
                result.setTitle(each.getTitle());
                result.setPrice(each.getPrice());
                result.setCategory(each.getCategory());
                list.add(result);
            }
        }
        return list;
    }
}
