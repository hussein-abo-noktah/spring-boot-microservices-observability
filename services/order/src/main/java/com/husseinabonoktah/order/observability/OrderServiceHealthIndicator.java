package com.husseinabonoktah.order.observability;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean serviceIsHealthy = checkServiceHealth();

        if (serviceIsHealthy) {
            return Health.up()
                    .withDetail("service", "order-service")
                    .withDetail("message", "Order service is running normally")
                    .build();
        }

        return Health.down()
                .withDetail("service", "order-service")
                .withDetail("message", "Order service has a problem")
                .build();
    }

    private boolean checkServiceHealth() {
        return true;
    }
}
