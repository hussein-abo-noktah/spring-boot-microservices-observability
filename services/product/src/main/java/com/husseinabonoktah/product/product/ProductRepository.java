package com.husseinabonoktah.product.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByIdInOrderById(List<Integer> ids);

    long countByAvailableQuantityLessThanEqual(double threshold);

    @Query("select coalesce(sum(p.availableQuantity), 0) from Product p")
    Double sumAvailableQuantity();
}
