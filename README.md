# Spring Boot Microservices Observability

This project is a Spring Boot microservices playground for building a complete observability stack step by step.

So far, the services are created and the observability stack includes Prometheus for metrics plus Grafana Alloy and Tempo for distributed tracing.

## Services

- Config Server
- Discovery Service
- Gateway Service
- Customer, Product, Order, and Payment services
- Tempo
- Loki
- Grafana Alloy
- Prometheus
- Grafana
- PostgreSQL and MongoDB

## Observability Status

- Spring Boot Actuator
- Micrometer Prometheus registry
- Spring Boot OpenTelemetry tracing
- OTLP trace export from services to Grafana Alloy
- Grafana Alloy receiving OTLP on `4317` and `4318`
- Tempo as the trace backend
- Loki as the centralized log backend
- Grafana Alloy collecting Docker logs into Loki and forwarding traces to Tempo
- Dedicated management ports for services
- Prometheus scrape configuration
- Grafana service on port `3000`
- Provisioned Prometheus datasource in Grafana
- Provisioned Tempo datasource in Grafana
- Provisioned Loki datasource in Grafana
- Preloaded dashboard: `Microservices Observability Overview`
- Preloaded dashboard: `Business Metrics Overview`
- Preloaded dashboard: `Tempo Trace Explorer`
- Preloaded dashboard: `Tempo Trace Statistics`
- Preloaded dashboard: `Loki Log Overview`
- Preloaded dashboard: `Loki Trace Correlation`
- Verified end-to-end distributed tracing through `gateway-service -> order-service -> customer-service -> product-service -> payment-service`

- `gateway-service:7071/actuator/prometheus`
- `customer-service:7073/actuator/prometheus`
- `order-service:7075/actuator/prometheus`
- `payment-service:7077/actuator/prometheus`
- `product-service:7079/actuator/prometheus`

## Grafana Dashboard

Grafana is available at [http://localhost:3000](http://localhost:3000) with the default credentials `admin` / `admin`.

The provisioned dashboards are in the `Microservices Observability` folder:

- `Microservices Observability Overview`
- `Business Metrics Overview`
- `Tempo Trace Explorer`
- `Tempo Trace Statistics`
- `Loki Log Overview`
- `Loki Trace Correlation`

It currently includes these panels:

- `Healthy Services`
- `Total Throughput`
- `5xx Error Rate`
- `Average API Latency`
- `Service Availability`
- `Request Rate by Service`
- `Average API Latency by Service`
- `5xx Error Rate by Service`
- `Top Endpoints by Throughput`
- `Slowest Endpoints by Average Latency`
- `JVM Heap Used`
- `Process CPU Usage`
- `GC Pause Time per Second`
- `DB Pool Active Utilization`

The business dashboard includes these KPI panels:

- `Orders Created`
- `Revenue Booked`
- `Average Order Value`
- `Payments Processed`
- `Customer Base`
- `Catalog Size`
- `Low Stock Products`
- `Inventory Units Available`
- `Revenue Per Minute`
- `Customer Lifecycle Events`
- `Orders By Payment Method`
- `Payment Volume By Method`
- `Inventory Units Reserved Per Minute`
- `Inventory Purchase Failures`
- `Order Failures`
- `Payment Failures`

The custom business metrics currently cover:

- Order creation totals, failure reasons, order value, and line-item counts
- Payment processing totals, failure reasons, and payment amounts
- Product catalog size, low-stock count, available inventory units, and inventory reservation activity
- Customer base count plus create, update, and delete activity

The Tempo dashboard includes these tracing views:

- `Recent Traces`
- `Error Traces`
- `Slow Traces`
- `Selected Trace`

The trace statistics dashboard includes:

- `Traced Request Rate`
- `Recent Traces`
- `Slow Traces`
- `Error Traces`
- `Trace Count by Service`
- `Slow Trace Count by Service`
- `Trace Rate by Service`
- `Most Recent Traces`
- `Most Recent Slow Traces`

The Loki overview dashboard includes:

- `Matching Log Lines`
- `Warn Or Error Lines`
- `Error Or Exception Lines`
- `Current Log Rate`
- `Log Rate By Service`
- `Selected Severity Rate By Service`
- `Noisiest Services`
- `Top Containers For Selected Severity`
- `Recent Matching Logs`
- `Recent Logs For Selected Severity`

The Loki trace correlation dashboard includes:

- `Trace Log Lines`
- `Warn Or Error Lines In Trace`
- `Current Trace Log Rate`
- `Trace Log Rate By Service`
- `Trace Issues By Service`
- `Logs For Selected Trace`
- `Issues Inside Selected Trace`

## Run

```bash
docker compose up --build -d
```

Prometheus: [http://localhost:9090](http://localhost:9090)
Grafana: [http://localhost:3000](http://localhost:3000)
Tempo API: [http://localhost:3200](http://localhost:3200)
Loki API: [http://localhost:3100](http://localhost:3100)
OTLP gRPC: `localhost:4317`
OTLP HTTP: `localhost:4318`

## Next Observability Steps

- Structured JSON logging for richer Loki parsing
- Alerting rules
- SLO-style dashboards and alert thresholds for business KPIs
