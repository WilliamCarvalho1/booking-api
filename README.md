# Booking API

## Some considerations:

- For the purpose of this exercise, this hotel has 3 floors and 3 rooms, only one of them is enabled to receive guests.
- The customers (guests) have to be previously inserted in the database via a "Registration Endpoint" (which I didn't developed bacause I think it's not in the scope of this task) in order to create a new reservation.
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