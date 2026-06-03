package com.husseinabonoktah.order.payment;



import com.husseinabonoktah.order.customer.CustomerResponse;
import com.husseinabonoktah.order.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {
}
