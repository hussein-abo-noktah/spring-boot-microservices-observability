package com.husseinabonoktah.product.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import com.husseinabonoktah.product.product.ProductRepository;

@Component
public class ProductBusinessMetrics {

    private static final double LOW_STOCK_THRESHOLD = 10.0d;

    private final MeterRegistry meterRegistry;

    public ProductBusinessMetrics(MeterRegistry meterRegistry, ProductRepository productRepository) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("business.products.catalog.count", productRepository, repository -> (double) repository.count())
                .description("Current number of products in the catalog")
                .register(meterRegistry);

        Gauge.builder("business.products.low.stock.count", productRepository,
                        repository -> (double) repository.countByAvailableQuantityLessThanEqual(LOW_STOCK_THRESHOLD))
                .description("Current number of low-stock products")
                .register(meterRegistry);

        Gauge.builder("business.inventory.available.units", productRepository, repository -> {
                    var totalUnits = repository.sumAvailableQuantity();
                    return totalUnits == null ? 0.0d : totalUnits;
                })
                .description("Current total available inventory units")
                .register(meterRegistry);
    }

    public void recordProductCreated(BigDecimal price) {
        Counter.builder("business.products.created")
                .description("Number of products added to the catalog")
                .register(meterRegistry)
                .increment();

        DistributionSummary.builder("business.products.price")
                .description("Catalog product price")
                .register(meterRegistry)
                .record(price.doubleValue());
    }

    public void recordInventoryPurchaseCompleted(double totalUnits, int lineItems) {
        Counter.builder("business.inventory.purchase.completed")
                .description("Number of successful inventory purchase operations")
                .register(meterRegistry)
                .increment();

        DistributionSummary.builder("business.inventory.units.reserved")
                .description("Total reserved inventory units per purchase operation")
                .register(meterRegistry)
                .record(totalUnits);

        DistributionSummary.builder("business.inventory.line.items")
                .description("Number of requested line items per purchase operation")
                .register(meterRegistry)
                .record(lineItems);
    }

    public void recordInventoryPurchaseFailure(String reason) {
        Counter.builder("business.inventory.purchase.failures")
                .description("Number of failed inventory purchase operations")
                .tag("reason", reason)
                .register(meterRegistry)
                .increment();
    }
}
