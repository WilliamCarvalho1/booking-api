CREATE TABLE customer(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO customer (first_name, last_name, phone, email)
VALUES ('Michael', 'Jordan', '1234-1234', 'michael.jordan@email.com');

INSERT INTO customer (first_name, last_name, phone, email)
VALUES ('Luka', 'Doncic', '2345-2345', 'luka.doncic@email.com');

INSERT INTO customer (first_name, last_name, phone, email)
VALUES ('Vince', 'Carter', '4321-4321', 'vince.carter@email.com');

INSERT INTO customer (first_name, last_name, phone, email)
VALUES ('Oscar', 'Schmidt', '9321-4321', 'oscar.schmidt@email.com');

INSERT INTO customer (first_name, last_name, phone, email)
VALUES ('Leandro', 'Barbosa', '9325-4321', 'leandro.barbosa@email.com');