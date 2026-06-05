# Spring Boot Microservices Observability

This project is a Spring Boot microservices playground for building a complete observability stack step by step.

So far, the services are created and Prometheus is integrated to collect metrics from the system.

## Services

- Config Server
- Discovery Service
- Gateway Service
- Customer, Product, Order, and Payment services
- Prometheus
- Grafana
- PostgreSQL and MongoDB

## Observability Status

- Spring Boot Actuator
- Micrometer Prometheus registry
- Dedicated management ports for services
- Prometheus scrape configuration
- Grafana service on port `3000`
- Provisioned Prometheus datasource in Grafana
- Provisioned Tempo datasource in Grafana
- Preloaded dashboard: `Microservices Observability Overview`
- Preloaded dashboard: `Tempo Trace Explorer`
- Preloaded dashboard: `Tempo Trace Statistics`

- `gateway-service:7071/actuator/prometheus`
- `customer-service:7073/actuator/prometheus`
- `order-service:7075/actuator/prometheus`
- `payment-service:7077/actuator/prometheus`
- `product-service:7079/actuator/prometheus`

## Grafana Dashboard

Grafana is available at [http://localhost:3000](http://localhost:3000) with the default credentials `admin` / `admin`.

The provisioned dashboards are in the `Microservices Observability` folder:

- `Microservices Observability Overview`
- `Tempo Trace Explorer`
- `Tempo Trace Statistics`

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

## Run

```bash
docker compose up --build -d
```

Prometheus: [http://localhost:9090](http://localhost:9090)
Grafana: [http://localhost:3000](http://localhost:3000)

## Next Observability Steps

- Centralized logging
- Custom business metrics
- Alerting rules
