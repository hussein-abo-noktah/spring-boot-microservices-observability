package com.husseinabonoktah.order.product;

import com.husseinabonoktah.order.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class ProductClient {


    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestClient restClient;

    public void purchaseProducts(List<PurchaseRequest> request) {
        ResponseEntity<List<PurchaseResponse>> responseEntity = restClient
                .post()
                .uri(productUrl + "/purchase")
                .contentType(APPLICATION_JSON)
                .body(request)
                .retrieve().toEntity(new ParameterizedTypeReference<>() {
                });
        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the products purchase: " + responseEntity.getStatusCode());
        }
    }
}
