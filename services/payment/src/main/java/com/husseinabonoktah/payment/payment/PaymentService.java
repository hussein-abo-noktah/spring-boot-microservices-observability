package com.husseinabonoktah.payment.payment;

import com.husseinabonoktah.payment.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {

    public Integer processPayment(PaymentRequest request) {
        if (request.customer().id() == null || request.customer().id().isBlank()) {
            throw new BusinessException("Customer id is required");
        }

        log.info(
                "Accepted payment request. orderId={}, orderReference={}, customerId={}, amount={}, paymentMethod={}",
                request.orderId(),
                request.orderReference(),
                request.customer().id(),
                request.amount(),
                request.paymentMethod()
        );

        return request.orderId();
    }
}
