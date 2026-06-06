package com.husseinabonoktah.customer.observability;

import com.husseinabonoktah.customer.customer.CustomerRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomerBusinessMetrics {

  private final MeterRegistry meterRegistry;

  public CustomerBusinessMetrics(MeterRegistry meterRegistry, CustomerRepository customerRepository) {
    this.meterRegistry = meterRegistry;

    Gauge.builder("business.customers.count", customerRepository, repository -> (double) repository.count())
        .description("Current number of customers")
        .register(meterRegistry);
  }

  public void recordCustomerCreated() {
    Counter.builder("business.customers.created")
        .description("Number of created customers")
        .register(meterRegistry)
        .increment();
  }

  public void recordCustomerUpdated() {
    Counter.builder("business.customers.updated")
        .description("Number of updated customers")
        .register(meterRegistry)
        .increment();
  }

  public void recordCustomerDeleted() {
    Counter.builder("business.customers.deleted")
        .description("Number of deleted customers")
        .register(meterRegistry)
        .increment();
  }
}
