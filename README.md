# Booking API

- A REST API for booking operations in a hotel

## Some considerations:

- For the purpose of this task, this hotel has 3 floors and 3 rooms.
- Only one of the rooms is available to receive guests having the flag "blocked" set to false.
- The room's number is its id.
- The rooms have to be previously inserted in the database, so the API can work properly.
- The customers (guests) have to be previously inserted in the database, so the API can work properly.
- Flyway is used to migrate the database, so we can test the API.
- The database runs in a Docker's MariaDB container.
- Due to unit testing the whole API is very time-consuming, I've just written a few unit tests.
- I would implement HATEOAS if I had more time.

### Swagger URL: http://localhost:8080/swagger-ui.html

## Queries to check the data:

- SELECT * FROM room;
- SELECT * FROM customer;
- SELECT * FROM reservation;
- SELECT * FROM flyway_schema_history;

## In case cleaning the database is needed:

- DROP TABLE reservation;
- DROP TABLE customer;
- DROP TABLE room;

- DELETE FROM flyway_schema_history;