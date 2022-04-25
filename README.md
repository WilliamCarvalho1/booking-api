# Booking API

## Some considerations:

- For the purpose of this task, this hotel has 3 floors and 3 rooms.
- The rooms have to be previously inserted in the database, so the API can work properly.
- Only one of the rooms is available to receive guests by the flag blocked set to "false".
- The room's number is its id.
- The customers (guests) have to be previously inserted in the database, so the API can work properly.
- Flyway is used to migrate the database, so we can test the API.
- The database runs in a Docker's MariaDB container.
- Due to unit testing the whole API is very time-consuming, I've just written a few unit tests. 

### Swagger URL: http://localhost:8080/swagger-ui.html