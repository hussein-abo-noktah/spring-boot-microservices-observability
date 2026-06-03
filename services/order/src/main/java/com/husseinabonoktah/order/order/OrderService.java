package com.husseinabonoktah.order.order;


import com.husseinabonoktah.order.customer.CustomerServiceClient;
import com.husseinabonoktah.order.exception.BusinessException;
import com.husseinabonoktah.order.orderitem.OrderItemRequest;
import com.husseinabonoktah.order.orderitem.OrderItemService;
import com.husseinabonoktah.order.payment.PaymentRequest;
import com.husseinabonoktah.order.payment.PaymentServiceClient;
import com.husseinabonoktah.order.product.ProductClient;
import com.husseinabonoktah.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerServiceClient customerClient;
    private final PaymentServiceClient paymentClient;
    private final ProductClient productClient;
    private final OrderItemService orderItemService;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderItemService.saveOrderItem(
                    new OrderItemRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        // add payment requests

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
