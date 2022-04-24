CREATE TABLE reservation(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT(20),
    room_id BIGINT(20),
    booking_date DATE NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_value DOUBLE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (room_id) REFERENCES room(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO reservation (customer_id, room_id, booking_date, check_in_date, check_out_date, status, total_value)
VALUES (1, 1, "2022/04/18", "2022/04/20", "2022/04/22", "active", 300.00);
INSERT INTO reservation (customer_id, room_id, booking_date, check_in_date, check_out_date, status, total_value)
VALUES (2, 1, "2022/04/18", "2022/04/25", "2022/04/26", "active", 100.00);
INSERT INTO reservation (customer_id, room_id, booking_date, check_in_date, check_out_date, status, total_value)
VALUES (3, 1, "2022/04/27", "2022/04/28", "2022/04/30", "active", 200.00);