# Booking API

## Some considerations:

Due to the lack of business details (which I think is intentional), I had to come up with some business rules:
- This hotel has 3 floors.
- Flyway is used to migrate the database.
- The database runs in a Docker container.

### Swagger URL: http://localhost:8080/swagger-ui.html

### JSONs to be used in Postman:

### PUT endpoint at localhost:8080/v1/booking/...
- The API receives a Json like in this example:
  {
...
  }
- It returns a Json like in this example:
  {
  "id": 1,
...
  }