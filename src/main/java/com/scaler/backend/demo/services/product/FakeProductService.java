package com.scaler.backend.demo.services.product;

import com.scaler.backend.demo.dtos.FakeStoreProductDTO;
import com.scaler.backend.demo.dtos.GenericProductDTO;
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

    private GenericProductDTO fromFakeStoreProductDTO(FakeStoreProductDTO data) {
        GenericProductDTO result = new GenericProductDTO();
        result.setImage(data.getImage());
        result.setDescription(data.getDescription());
        result.setTitle(data.getTitle());
        result.setPrice(data.getPrice());
        result.setCategory(data.getCategory());
        return result;
    }

    @Override
    public GenericProductDTO createProduct(GenericProductDTO payload) {
        FakeStoreProductDTO body = restTemplateBuilder
                .build()
                .postForEntity(
                        productsUrl,
                        payload,
                        FakeStoreProductDTO.class
                ).getBody();

        if (body == null) {
            return null;
        }

        return fromFakeStoreProductDTO(body);
    }

    @Override
    public GenericProductDTO deleteProductById(Long id) {
        restTemplateBuilder.build().delete(getProductRequestUrl, id);
        return new GenericProductDTO();
    }

    @Override
    public GenericProductDTO updateProductById(Long id, GenericProductDTO payload) {

        RequestCallback requestCallback = restTemplateBuilder.build().acceptHeaderRequestCallback(FakeStoreProductDTO.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDTO>> responseExtractor = restTemplateBuilder.build().responseEntityExtractor(FakeStoreProductDTO.class);
        ResponseEntity<FakeStoreProductDTO> response = restTemplateBuilder.build().execute(productsUrl, HttpMethod.PUT, requestCallback, responseExtractor, payload, id);

        if (response != null && response.getBody() != null) {
            return fromFakeStoreProductDTO(response.getBody());
        }

        return null;
    }


    @Override
    public List<GenericProductDTO> getProducts() {
        ResponseEntity<FakeStoreProductDTO[]> response = restTemplateBuilder.build().getForEntity(productsUrl, FakeStoreProductDTO[].class);
        List<GenericProductDTO> list = new ArrayList<>();

        if (response.getBody() != null) {
            for (FakeStoreProductDTO each : response.getBody()) {
                list.add(fromFakeStoreProductDTO(each));
            }
        }
        return list;
    }
}
