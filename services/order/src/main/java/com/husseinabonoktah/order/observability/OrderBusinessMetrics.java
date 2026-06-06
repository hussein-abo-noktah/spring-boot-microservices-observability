package com.husseinabonoktah.order.observability;

import com.husseinabonoktah.order.order.OrderRepository;
import com.husseinabonoktah.order.order.PaymentMethod;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderBusinessMetrics {

    private final MeterRegistry meterRegistry;

    public OrderBusinessMetrics(MeterRegistry meterRegistry, OrderRepository orderRepository) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("business.orders.count", orderRepository, repository -> (double) repository.count())
                .description("Current number of persisted orders")
                .register(meterRegistry);
    }

    public void recordOrderCreated(BigDecimal amount, PaymentMethod paymentMethod, int lineItems) {
        var paymentMethodTag = paymentMethod == null ? "unknown" : paymentMethod.name().toLowerCase();

        Counter.builder("business.orders.created")
                .description("Number of successfully created orders")
                .tag("payment_method", paymentMethodTag)
                .register(meterRegistry)
                .increment();

        DistributionSummary.builder("business.orders.amount")
                .description("Booked order amount")
                .tag("payment_method", paymentMethodTag)
                .register(meterRegistry)
                .record(amount.doubleValue());

        DistributionSummary.builder("business.orders.line.items")
                .description("Number of line items per order")
                .register(meterRegistry)
                .record(lineItems);
    }

    public void recordOrderFailure(String reason) {
        Counter.builder("business.orders.failed")
                .description("Number of failed order creation attempts")
                .tag("reason", reason)
                .register(meterRegistry)
                .increment();
    }
}
