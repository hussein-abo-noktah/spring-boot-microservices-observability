package com.husseinabonoktah.product.product;

import com.husseinabonoktah.product.observability.ProductBusinessMetrics;
import com.husseinabonoktah.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductBusinessMetrics businessMetrics;

    public Integer createProduct(
            ProductRequest request
    ) {
        var product = mapper.toProduct(request);
        var createdProduct = repository.save(product);
        businessMetrics.recordProductCreated(request.price());
        return createdProduct.getId();
    }

    public ProductResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        try {
            return purchaseAndRecordMetrics(request);
        } catch (ProductPurchaseException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            businessMetrics.recordInventoryPurchaseFailure("inventory_processing_failed");
            throw ex;
        }
    }

    private List<ProductPurchaseResponse> purchaseAndRecordMetrics(List<ProductPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            businessMetrics.recordInventoryPurchaseFailure("products_missing");
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                businessMetrics.recordInventoryPurchaseFailure("insufficient_stock");
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        businessMetrics.recordInventoryPurchaseCompleted(
                request.stream().mapToDouble(ProductPurchaseRequest::quantity).sum(),
                request.size()
        );
        return purchasedProducts;
    }

}
