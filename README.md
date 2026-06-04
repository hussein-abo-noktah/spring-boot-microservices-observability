# Spring Boot Microservices Observability

This project is a Spring Boot microservices playground for building a complete observability stack step by step.

So far, the services are created and Prometheus is integrated to collect metrics from the system.

## Services

- Config Server
- Discovery Service
- Gateway Service
- Customer, Product, Order, and Payment services
- Prometheus
- PostgreSQL and MongoDB

## Observability Status

- Spring Boot Actuator
- Micrometer Prometheus registry
- Dedicated management ports for services
- Prometheus scrape configuration

- `gateway-service:7071/actuator/prometheus`
- `customer-service:7073/actuator/prometheus`
- `order-service:7075/actuator/prometheus`
- `payment-service:7077/actuator/prometheus`
- `product-service:7079/actuator/prometheus`

## Run

```bash
docker compose up --build -d
```

Prometheus: [http://localhost:9090](http://localhost:9090)

## Next Observability Steps

- Grafana dashboards
- Distributed tracing
- Alerting rules
- Centralized logging
- Custom business metrics
