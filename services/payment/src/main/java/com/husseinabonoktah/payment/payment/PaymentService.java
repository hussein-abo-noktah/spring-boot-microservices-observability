package com.husseinabonoktah.payment.payment;

import com.husseinabonoktah.payment.exception.BusinessException;
import com.husseinabonoktah.payment.observability.PaymentBusinessMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public final PaymentRepository repository;
    public final PaymentMapper mapper;
    public final PaymentBusinessMetrics businessMetrics;

    public Integer processPayment(PaymentRequest request) {
        if (request.customer().id() == null || request.customer().id().isBlank()) {
            businessMetrics.recordPaymentFailure("missing_customer");
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

        Payment payment = mapper.toPayment(request);

        Payment savedPayment;
        try {
            savedPayment = repository.save(payment);
        } catch (RuntimeException ex) {
            businessMetrics.recordPaymentFailure("payment_persistence_failed");
            throw ex;
        }

        businessMetrics.recordPaymentProcessed(request.amount(), request.paymentMethod());
        return savedPayment.getId();
    }
}
