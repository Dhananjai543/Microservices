# Order-Service and Address-Service

This project demonstrates the use of **Resilience4j Circuit Breaker** for fault tolerance in a microservices architecture. The **Order Service** depends on the **Address Service** to fetch address details based on the postal code. If the **Address Service** is down or unresponsive, the Circuit Breaker will prevent further calls and return a fallback response.

## Resilience4j Circuit Breaker Configuration

### Configuration Properties Explained:

| Property | Description |
|----------|-------------|
| **resilience4j.circuitbreaker.instances.order-service.sliding-window-type=COUNT_BASED** | Defines the type of sliding window to measure failures. Here, it is **COUNT_BASED**, meaning failures are counted over a fixed number of requests. |
| **resilience4j.circuitbreaker.instances.order-service.failure-rate-threshold=50** | If **50%** of the requests fail, the circuit breaker transitions to the **OPEN** state. |
| **resilience4j.circuitbreaker.instances.order-service.minimum-number-of-calls=5** | The circuit breaker only starts evaluating failures after at least **5** calls. |
| **resilience4j.circuitbreaker.instances.order-service.automatic-transition-from-open-to-half-open-enabled=true** | Allows the circuit breaker to automatically transition from **OPEN** to **HALF-OPEN** state to check if the service has recovered. |
| **resilience4j.circuitbreaker.instances.order-service.wait-duration-in-open-state=5s** | The circuit breaker stays in the **OPEN** state for **5 seconds** before transitioning to **HALF-OPEN** to test service recovery. |
| **resilience4j.circuitbreaker.instances.order-service.permitted-number-of-calls-in-half-open-state=3** | In **HALF-OPEN** state, allows **3** test calls to determine if the service is healthy. |
| **resilience4j.circuitbreaker.instances.order-service.sliding-window-size=10** | The sliding window considers the last **10** requests for failure rate calculation. |
| **resilience4j.circuitbreaker.instances.order-service.register-health-indicator=true** | Enables health monitoring for the circuit breaker in **Spring Boot Actuator**. |

## Usage

### Address Service
- **Endpoint:** `http://localhost:9090/addresses/135106`
- **Response:**
  ```json
  {
      "id": 1,
      "postalCode": "135106",
      "state": "Haryana",
      "city": "Yamunanagar"
  }
  ```

### Order Service
- **Endpoint:** `http://localhost:1010/orders?orderNumber=0c70c0c2`
- **Response (Success Case):**
  ```json
  {
      "id": 1,
      "orderNumber": "0c70c0c2",
      "postalCode": "135106",
      "shippingState": "Haryana",
      "shippingCity": "Yamunanagar"
  }
  ```

- **Response (Address Service Down - Circuit Breaker Open):**
  ```json
  {
      "msg": "Address service is not responding properly"
  }
  ```

## Actuator Health Check
- **Endpoint:** `http://localhost:1010/actuator/health`
- **Sample Response:**
  ```json
  {
      "status": "UP",
      "components": {
          "circuitBreakers": {
              "status": "UP",
              "details": {
                  "order-service": {
                      "status": "UP",
                      "details": {
                          "failureRate": "-1.0%",
                          "failureRateThreshold": "50.0%",
                          "slowCallRate": "-1.0%",
                          "slowCallRateThreshold": "100.0%",
                          "bufferedCalls": 2,
                          "slowCalls": 0,
                          "slowFailedCalls": 0,
                          "failedCalls": 1,
                          "notPermittedCalls": 0,
                          "state": "CLOSED"
                      }
                  }
              }
          },
          "db": {
              "status": "UP",
              "details": {
                  "database": "H2",
                  "validationQuery": "isValid()"
              }
          },
          "diskSpace": {
              "status": "UP",
              "details": {
                  "total": 510976659456,
                  "free": 211542556672,
                  "threshold": 10485760,
                  "exists": true
              }
          }
      }
  }
  ```

## Summary
This project showcases how **Resilience4j Circuit Breaker** prevents cascading failures by monitoring dependent services and providing fallback responses when necessary. The **actuator health check** also provides insights into the circuit breaker's state.

---

### 🚀 Happy Coding!

