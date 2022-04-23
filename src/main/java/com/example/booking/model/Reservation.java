package com.example.booking.model;

import com.example.booking.enums.ReservationStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parking_registry")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "total_value")
    private BigDecimal totalValue;

}
