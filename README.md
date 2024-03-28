# Coffee Store

## Introduction

This application is a simple backend for an online coffee store, where users can place drinks with
toppings orders, and admins can create/update/delete drinks/toppings and have access to the most used
toppings. It also supports a discount program for the clients that order more than average.

This sample demo uses the in-memory HSQL database for storing the data. After running, you can browse
the API via the swagger web UI at
address [localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Discount Strategy

Using `Strategy Pattern` helps the development supporting different discount programs. Following class diagram
shows the relations:

![Discount Strategy](/doc/discount-strategy.png)

## Requirements

- Java 17 to run the project using maven wrapper
- Docker 20+ to build the image, and then run it

## Run the Project

### Using the maven wrapper

Run the following command in the root of project:

```shell
./mvnw spring-boot:run
```

The application will start up using port `8080`.

## Calling REST Endpoints

This application uses a minimum security mechanism for authentication and authorization. Passing any value for
HTTP header `Access-Token` authenticates the request as a customer. If you pass `admin` as access token, the current
request is processed with `Admin` access.

- List of drinks
  ```shell
  curl -X 'GET' \
    'http://localhost:8080/api/drinks' \
    -H 'accept: */*' \
    -H 'Access-Token: test'
  ```

- Put an order - purchasing 5 drinks of `Black Coffee` with `Milk` as its topping
  ```shell
  curl -X 'POST' \
    'http://localhost:8080/api/ordering/purchases' \
    -H 'accept: */*' \
    -H 'Access-Token: test' \
    -H 'Content-Type: application/json' \
    -d '{
    "drinks": [
      {
        "id": 1,
        "toppings": [
          {
            "id": 1
          }
        ],
        "count": 5
      }
    ]
  }'
  ```

- Most used toppings with drinks using admin access
  ```shell
  curl -X 'GET' \
    'http://localhost:8080/api/toppings/stats' \
    -H 'accept: */*' \
    -H 'Access-Token: admin'
  ```

- Actuator
  ```shell
  curl -X 'GET' \
    'http://localhost:8080/actuator/health' \
    -H 'accept: */*'
  ```

## Project Test

For simplicity, there are three tests with different approach:

- `TestDiscountStrategies` - simple JUnit tests for discount strategies
- `TestCartService` - simple mocking for testing `CartService`
- `TestCartAndStat` - simple integration test for saving carts and then getting the stat for toppings

