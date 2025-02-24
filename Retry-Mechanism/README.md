# Retry Mechanism in Order-Service

This project demonstrates the **Resilience4j Retry Mechanism** in a microservices setup. The **Order Service** depends on the **Address Service** to fetch address details based on the postal code. If the **Address Service** is temporarily unavailable, the **Retry Mechanism** ensures multiple retry attempts before returning an error response.

## Retry Configuration

| Property | Description |
|----------|-------------|
| **resilience4j.retry.instances.order-service.max-attempts=3** | The maximum number of retry attempts before failing. Here, the request will be retried **3 times**. |
| **resilience4j.retry.instances.order-service.wait-duration=1s** | The wait time between retry attempts. Here, the service waits **1 second** before retrying. |
| **resilience4j.retry.instances.order-service.retry-exceptions[0]=org.springframework.web.client.HttpServerErrorException** | Specifies the exceptions for which the retry mechanism should trigger. In this case, retries occur for **HTTP 5xx errors**. |
| **resilience4j.retry.instances.order-service.ignore-exceptions[0]=org.springframework.web.client.ResourceAccessException** | Specifies exceptions that should be ignored and **not retried**. Here, connection issues (e.g., network failure) are not retried. |

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
- **Endpoint:** `http://localhost:1010/orders?orderNumber=dhja882d`
- **Response (Address Not Found, Triggering Retry):**
  ```json
  {
      "msg": "Address service is not responding properly"
  }
  ```

## Retry Monitoring APIs

### Check Retry Events
- **Endpoint:** `http://localhost:1010/actuator/retryevents`
- **Sample Response:**
  ```json
  {
      "retryEvents": [
          {
              "retryName": "order-service",
              "type": "RETRY",
              "creationTime": "2025-02-24T09:42:36.196+05:30",
              "errorMessage": "500 Internal Server Error",
              "numberOfAttempts": 1
          },
          {
              "retryName": "order-service",
              "type": "RETRY",
              "creationTime": "2025-02-24T09:42:37.223+05:30",
              "errorMessage": "500 Internal Server Error",
              "numberOfAttempts": 2
          },
          {
              "retryName": "order-service",
              "type": "ERROR",
              "creationTime": "2025-02-24T09:42:38.250+05:30",
              "errorMessage": "500 Internal Server Error",
              "numberOfAttempts": 3
          }
      ]
  }
  ```

### List Registered Retries
- **Endpoint:** `http://localhost:1010/actuator/retries`
- **Sample Response:**
  ```json
  {
      "retries": [
          "order-service"
      ]
  }
  ```

### Check Retry Events for Order Service
- **Endpoint:** `http://localhost:1010/actuator/retryevents/order-service`

### Retry Metrics
- **Endpoint:** `http://localhost:1010/actuator/metrics/resilience4j.retry.calls`
- **Sample Response:**
  ```json
  {
      "name": "resilience4j.retry.calls",
      "description": "The number of failed calls without a retry attempt",
      "measurements": [
          {
              "statistic": "COUNT",
              "value": 1.0
          }
      ],
      "availableTags": [
          {
              "tag": "kind",
              "values": [
                  "successful_without_retry",
                  "successful_with_retry",
                  "failed_with_retry",
                  "failed_without_retry"
              ]
          },
          {
              "tag": "name",
              "values": [
                  "order-service"
              ]
          }
      ]
  }
  ```

## Summary
The **Resilience4j Retry Mechanism** improves service reliability by automatically retrying requests on transient failures. This setup ensures that temporary issues do not result in immediate errors but instead give the dependent service time to recover.

---

### 🚀 Happy Coding!

