# Raw Material Service

Part of a distributed **Industry 4.0 Industrial Pipeline Simulator**, built with an event-driven microservices architecture.

---

## 🧠 System Overview

This project simulates a real industrial production pipeline, where independent services collaborate to produce goods.

Pipeline Flow:

Raw Material → Processing → Component Production → Product Assembly

Each step operates as an isolated microservice, communicating through Kafka events.

---

## 🎯 Function of this Service

The **Raw Material Service** is the entry point of the production pipeline.

It is responsible for simulating the extraction of raw materials (e.g., iron, latex, sand) that will be used by subsequent services.

Without this service, the entire production chain cannot be initiated.

---

## ⚙️ Responsibilities

- Simulate raw material extraction using time-based pipelines
- Validate received production orders
- Persist extracted materials
- Publish events in Kafka for subsequent processing

---

## 🔄 Pipeline Position
[Raw Material Service] → [Processing Service] → [Component Service] → [Assembly Service]

---

## 📡 Event-Driven Communication

### Events Produced

- `RAW_MATERIAL_EXTRACTED`

### Event Structure

```
{

"eventId": "uuid",

"eventType": "RAW_MATERIAL_EXTRACTED",

"timestamp": 1710000000,

"sourceService": "raw-material-service",

"targetService": "component-service",

"payload": {
"name": "Iron",

"quantity": 10

}
}
```

## ⏱️ Production Pipeline (Latency Simulation)

Production is not instantaneous. Each request goes through a pipeline with defined durations.

Example

```
[
{ "name": "EXTRACTION", "durationMs": 10000 },

{ "name": "TRANSPORT", "durationMs": 5000 }

```

This simulates real industrial delays.

## 🔄 Internal Flow
- Receive HTTP request
- Validate input data
- Execute production pipeline (with delay)
- Persist raw material in the database
- Publish Kafka event

## 🗄️ Data Ownership

This service follows microservices best practices:

- Own database
- No direct access to data from other services
- Communication strictly via events

## 🧱 Technologies
- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker

## Running the Service
docker-compose up --build

## 🧠 Key Concepts Demonstrated
- Event-driven architecture
- Distributed systems design
- Simulation of a production pipeline with latency
- Service isolation and data ownership

## Other Services:
- [processing-service](https://github.com/Valdemar-Andrade/processing-service.git)
- [component-service](https://github.com/Valdemar-Andrade/component-service.git)

## 👤 Developer
- GitHub: [@Valdemar-Andrade]
- LinkedIn: [Valdemar Andrade](https://www.linkedin.com/in/valdemar-andrade-8b0b45189)
