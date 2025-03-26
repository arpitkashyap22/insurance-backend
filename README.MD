# Insurance Purchase System

## Overview
A comprehensive backend system for managing insurance products, purchases, and policy document generation.

## Features
- List all available insurances
- Purchase insurances
- Download policy documents
- Curated insurance recommendations

## Technologies Used
- Java 17
- Spring Boot 3.2
- H2 Database
- JPA/Hibernate
- iText PDF Generation
- Swagger OpenAPI

## Setup and Running
1. Clone the repository
2. Ensure Java 17 is installed
3. Run `mvn clean install`
4. Start the application with `mvn spring-boot:run`

## API Endpoints
- `/api/insurance/list`: Get all insurances
- `/api/insurance/curated`: Get personalized insurances
- `/api/insurance/purchase`: Purchase an insurance
- `/api/insurance/download/{transactionId}`: Download policy document

## Swagger Documentation
Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Deployment
Can be deployed on any Java-compatible cloud platform like AWS, Azure, or Heroku.



# CURL Operations for Insurance Purchase System

## 1. List All Insurances
```bash
curl -X GET http://localhost:8080/api/insurance/list
```

## 2. Get Curated Insurances
```bash
# Example for a young female professional
curl -X GET "http://localhost:8080/api/insurance/curated?age=30&gender=FEMALE&income=350000"

# Example for a senior
curl -X GET "http://localhost:8080/api/insurance/curated?age=60&gender=MALE&income=500000"
```

## 3. Purchase Insurance
```bash
# Purchase for a young adult
curl -X POST http://localhost:8080/api/insurance/purchase \
     -H "Content-Type: application/json" \
     -d '{
         "insuranceId": 1,
         "customerName": "John Doe",
         "customerEmail": "john.doe@example.com",
         "age": 30,
         "gender": "MALE",
         "income": 350000
     }'

# Purchase for a female customer
curl -X POST http://localhost:8080/api/insurance/purchase \
     -H "Content-Type: application/json" \
     -d '{
         "insuranceId": 3,
         "customerName": "Jane Smith",
         "customerEmail": "jane.smith@example.com",
         "age": 35,
         "gender": "FEMALE",
         "income": 500000
     }'
```

## 4. Download Policy Document
```bash
# Note: You'll need to replace {transactionId} with an actual transaction ID 
# received from a successful purchase
curl -X GET http://localhost:8080/api/insurance/download/{transactionId} \
     -O  # This saves the file locally
```

## 5. Error Scenario Examples

### Purchasing Insurance with Ineligible Criteria
```bash
# Trying to purchase an insurance not matching age/gender/income
curl -X POST http://localhost:8080/api/insurance/purchase \
     -H "Content-Type: application/json" \
     -d '{
         "insuranceId": 3,  # Women's Wellness Insurance
         "customerName": "John Doe",
         "customerEmail": "john.doe@example.com",
         "age": 30,
         "gender": "MALE",
         "income": 200000
     }'
```

### Downloading Non-Existent Policy
```bash
curl -X GET http://localhost:8080/api/insurance/download/invalid-transaction-id
```

## Swagger UI
You can also interact with the APIs through Swagger UI at:
`http://localhost:8080/swagger-ui.html`

## Postman Collection
For more comprehensive API testing, consider importing these curl commands into Postman.

## Testing Tips
- Always check the response for transaction ID when purchasing
- Use the transaction ID for policy document download
- Verify eligibility criteria before purchase