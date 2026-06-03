package com.husseinabonoktah.order.orderitem;

import com.husseinabonoktah.order.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper {
    public OrderItem toOrderItem(OrderItemRequest request) {
        return OrderItem.builder()
                .id(request.id())
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getQuantity()
        );
    }
}
