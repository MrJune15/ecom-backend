package com.scaler.backend.demo.services.product;

import com.scaler.backend.demo.dtos.FakeStoreProductDTO;
import com.scaler.backend.demo.dtos.GenericProductDTO;
import com.scaler.backend.demo.exceptions.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FakeProductService implements ProductService {


    private final String productsUrl = "https://fakestoreapi.com/products";

    private final String getProductRequestUrl = productsUrl + "/{id}";

    @NonNull
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public GenericProductDTO getProductById(Long id) throws NotFoundException {
        FakeStoreProductDTO body = restTemplateBuilder.build().getForEntity(getProductRequestUrl, FakeStoreProductDTO.class, id).getBody();
        if (body == null) {
            throw new NotFoundException("Didn't find any product with ID : " + id);
        }
        return fromFakeStoreProductDTO(body);
    }

    private GenericProductDTO fromFakeStoreProductDTO(FakeStoreProductDTO data) {
        GenericProductDTO result = new GenericProductDTO();
        result.setImage(data.getImage());
        result.setDescription(data.getDescription());
        result.setTitle(data.getTitle());
        result.setPrice(data.getPrice());
        result.setCategory(data.getCategory());
        return result;
    }

    private FakeStoreProductDTO fromGenericProductDTO(GenericProductDTO data) {
        FakeStoreProductDTO result = new FakeStoreProductDTO();
        result.setImage(data.getImage());
        result.setDescription(data.getDescription());
        result.setTitle(data.getTitle());
        result.setPrice(data.getPrice());
        result.setCategory(data.getCategory());
        return result;
    }

    @Override
    public GenericProductDTO createProduct(GenericProductDTO payload) throws NotFoundException {
        FakeStoreProductDTO body = restTemplateBuilder
                .build()
                .postForEntity(
                        productsUrl,
                        payload,
                        FakeStoreProductDTO.class
                ).getBody();

        if (body == null) {
            throw new NotFoundException("Failed to create product");
        }
        return fromFakeStoreProductDTO(body);
    }

    @Override
    public GenericProductDTO deleteProductById(Long id) throws NotFoundException {
        restTemplateBuilder.build().delete(getProductRequestUrl, id);
        RequestCallback requestCallback = restTemplateBuilder.build().acceptHeaderRequestCallback(FakeStoreProductDTO.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDTO>> responseExtractor = restTemplateBuilder.build().responseEntityExtractor(FakeStoreProductDTO.class);
        ResponseEntity<FakeStoreProductDTO> response = restTemplateBuilder.build().execute(productsUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("Didn't find any product with ID : " + id + " to delete.");
        }

        return fromFakeStoreProductDTO(response.getBody());

    }

    @Override
    public GenericProductDTO updateProductById(Long id, GenericProductDTO payload) throws NotFoundException {

        RequestCallback requestCallback = restTemplateBuilder.build().acceptHeaderRequestCallback(FakeStoreProductDTO.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDTO>> responseExtractor = restTemplateBuilder.build().responseEntityExtractor(FakeStoreProductDTO.class);
        ResponseEntity<FakeStoreProductDTO> response = restTemplateBuilder.build().execute(productsUrl, HttpMethod.PUT, requestCallback, responseExtractor, fromGenericProductDTO(payload), id);

        if (response == null || response.getBody() == null) {
            throw new NotFoundException("Didn't find any product with ID : " + id + " to update.");
        }

        return fromFakeStoreProductDTO(response.getBody());
    }


    @Override
    public List<GenericProductDTO> getProducts() throws NotFoundException {
        ResponseEntity<FakeStoreProductDTO[]> response = restTemplateBuilder.build().getForEntity(productsUrl, FakeStoreProductDTO[].class);

        if (response.getBody() == null) {
            throw new NotFoundException("No Products available");
        }
        List<GenericProductDTO> list = new ArrayList<>();
        for (FakeStoreProductDTO each : response.getBody()) {
            list.add(fromFakeStoreProductDTO(each));
        }
        return list;
    }
}
