#  Polyglot distributed sms-system

A distributed **SMS Processing System** built using **Spring Boot (Java)** and **Go**, communicating via **Kafka**, with persistence in **MongoDB** and caching using **Redis**.

---

##  Architecture Overview

```
Client
  ↓
Java SMS Producer (Spring Boot)
  ↓
Kafka
  ↓
Go SMS Consumer
  ↓
MongoDB
```

---

## Services Overview

###  SMS Producer (Java / Spring Boot)

**Responsibilities:**
- Accepts SMS send requests
- Checks if a user is blocked (via Redis)
- Sends SMS via a mock third-party service
- Publishes events to Kafka

---

###  SMS Consumer (Go)

**Responsibilities:**
- Consumes SMS events from Kafka
- Stores events in MongoDB
- Exposes REST API to retrieve SMS history by phone number

---

## Prerequisites

- Java 17+
- Go 1.21+
- Docker & Docker Compose
- Kafka
- MongoDB
- Redis

---

##  Running the System Locally

###  Start Infrastructure (Kafka, MongoDB, Redis)

```bash
docker compose up -d
```

---

###  Run Java SMS Producer

```bash
cd kafka-spring-demo/demo
mvn clean spring-boot:run
```

Service URL: http://localhost:8080

---

###  Run Go SMS Consumer

```bash
cd sms-consumer-go
go run cmd/main.go
```

Service URL: http://localhost:8081

---

##  API Endpoints

### 📤 Send SMS (Java Producer)

```bash
curl -X POST "http://localhost:8080/sms/send?phone=1&message=hello"
```

Response:
```json
{
  "phone": "1",
  "message": "hello",
  "status": "SUCCESS"
}
```

---

###  Retrieve SMS History (Go Consumer)

```bash
curl http://localhost:8081/v1/sms/user/1
```

Response:
```json
[
  {
    "phone": "1",
    "message": "hello",
    "status": "SUCCESS"
  }
]
```

---

###  Block a User

```bash
curl -X POST "http://localhost:8080/admin/block/2"
```

Attempt to send SMS:
```bash
curl -X POST "http://localhost:8080/sms/send?phone=2&message=hello"
```

Response:
```json
{
  "code": "USER_BLOCKED",
  "message": "User is blocked"
}
```

---

##  Running Unit Tests

### Java Tests

```bash
cd kafka-spring-demo/demo
mvn test
```

### Go Tests

```bash
cd sms-consumer-go
go test ./internal/service
```

---

