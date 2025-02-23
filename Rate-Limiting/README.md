# Payment Service

## Overview

The **Payment Service** is a Spring Boot application that integrates with a **payment processor** and implements **rate limiting** using **Resilience4j**. This prevents excessive requests and ensures system stability.

---

## **Rate Limiting Properties** (from `application.properties`)

The application uses **Resilience4j's RateLimiter** to limit incoming requests to the `payment-service`. The following properties control the rate limiting behavior:

| **Property**                                                                   | **Description**                                             | **Current Value** |
| ------------------------------------------------------------------------------ | ----------------------------------------------------------- | ----------------- |
| `resilience4j.ratelimiter.instances.payment-service.limit-for-period`          | Maximum requests allowed in one refresh period              | `5`               |
| `resilience4j.ratelimiter.instances.payment-service.limit-refresh-period`      | Time window before request limit resets                     | `15s`             |
| `resilience4j.ratelimiter.instances.payment-service.timeout-duration`          | How long a request waits if blocked before failing          | `5s`              |
| `resilience4j.ratelimiter.instances.payment-service.register-health-indicator` | Enables monitoring via Spring Actuator (`/actuator/health`) | `true`            |

### **How Rate Limiting Works:**

1. **Up to 5 requests** can be made in **15 seconds**.
2. If a 6th request arrives within 15s, it **waits up to 5 seconds** to check availability.
3. If no slot is free after **5 seconds**, the request **fails immediately**.
4. The `/actuator/health` endpoint shows rate limiter status.

---

## **Usage**

### **1. Payment Processor API**

This API processes payments received from the **payment-service**.

- **Endpoint:**
  ```
  POST http://localhost:1010/api/v1/processor-payment
  ```
- **Request Body:**
  ```json
  "Payment Information"
  ```

---

### **2. Payment Service API**

This API accepts payment requests and forwards them to the **payment processor**. It is protected by **rate limiting**.

- **Endpoint:**
  ```
  POST http://localhost:9090/api/v1/payment-service?paymentInfo=Payment by Dhananjai
  ```
- **Request Parameter:**
    - `paymentInfo` → `Payment by Dhananjai`

---

## **Monitoring Rate Limiting Status**

Check the rate limiter status via **Spring Actuator**:

```bash
GET http://localhost:9090/actuator/health
```

Example Response:

```json
{
  "status": "UP",
  "components": {
    "ratelimiters": {
      "status": "UP",
      "details": {
        "payment-service": {
          "availablePermissions": 3,
          "numberOfWaitingThreads": 0
        }
      }
    }
  }
}
```

---

