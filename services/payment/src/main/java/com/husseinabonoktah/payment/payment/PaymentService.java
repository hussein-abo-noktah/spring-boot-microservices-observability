package com.husseinabonoktah.payment.payment;

import com.husseinabonoktah.payment.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public final PaymentRepository repository;
    public final PaymentMapper mapper;
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

        Payment payment = mapper.toPayment(request);

        Payment savedPayment = repository.save(payment);
        return savedPayment.getId();
    }
}
