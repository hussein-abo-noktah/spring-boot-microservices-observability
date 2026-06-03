package com.husseinabonoktah.order.orderitem;

public record OrderItemRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {
}
