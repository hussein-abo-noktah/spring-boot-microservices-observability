package com.husseinabonoktah.payment.observability;

import com.husseinabonoktah.payment.payment.PaymentMethod;
import com.husseinabonoktah.payment.payment.PaymentRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentBusinessMetrics {

    private final MeterRegistry meterRegistry;

    public PaymentBusinessMetrics(MeterRegistry meterRegistry, PaymentRepository paymentRepository) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("business.payments.count", paymentRepository, repository -> (double) repository.count())
                .description("Current number of persisted payments")
                .register(meterRegistry);
    }

    public void recordPaymentProcessed(BigDecimal amount, PaymentMethod paymentMethod) {
        var paymentMethodTag = paymentMethod == null ? "unknown" : paymentMethod.name().toLowerCase();

        Counter.builder("business.payments.processed")
                .description("Number of successfully processed payments")
                .tag("payment_method", paymentMethodTag)
                .register(meterRegistry)
                .increment();

        DistributionSummary.builder("business.payments.amount")
                .description("Processed payment amount")
                .tag("payment_method", paymentMethodTag)
                .register(meterRegistry)
                .record(amount.doubleValue());
    }

    public void recordPaymentFailure(String reason) {
        Counter.builder("business.payments.failed")
                .description("Number of failed payment attempts")
                .tag("reason", reason)
                .register(meterRegistry)
                .increment();
    }
}
