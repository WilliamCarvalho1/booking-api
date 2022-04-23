CREATE TABLE reservation(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT(20) FOREIGN KEY,
    room_id BIGINT(20) FOREIGN KEY,
    booking_date DATE NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_value DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO reservation (customer_id, room_id, booking_date, check_in_date, check_out_date, total_value)
VALUES (1, 1, "18/04/2022", "20/04/2022", "23/04/2022", 300.00);
INSERT INTO reservation (customer_id, room_id, booking_date, check_in_date, check_out_date, total_value)
VALUES (2, 1, "18/04/2022", "25/04/2022", "26/04/2022", 100.00);
INSERT INTO reservation (customer_id, room_id, booking_date, check_in_date, check_out_date, total_value)
VALUES (3, 1, "27/04/2022", "28/04/2022", "30/04/2022", 200.00);