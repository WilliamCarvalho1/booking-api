CREATE TABLE room(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    floor INT NOT NULL,
    type VARCHAR(20) NOT NULL,
    price DOUBLE NOT NULL,
    flag_available TINYINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO room (floor, type, price, flag_available) VALUES (0, 'single', 100.00, true);
INSERT INTO room (floor, type, price, flag_available) VALUES (1, 'double', 200.00, false);
INSERT INTO room (floor, type, price, flag_available) VALUES (2, 'master', 300.00, false);