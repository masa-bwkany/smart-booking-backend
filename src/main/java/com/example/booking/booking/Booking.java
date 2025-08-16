package com.example.booking.booking;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="bookings")
@Getter @Setter @NoArgsConstructor
public class Booking {
	public enum Status { PENDING, CONFIRMED, CANCELLED }

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

	@Column(name="user_id",     nullable=false) private Long userId;
	@Column(name="provider_id", nullable=false) private Long providerId;
	@Column(name="service_id",  nullable=false) private Long serviceId;

	@Column(name="start_datetime", nullable=false) private Instant startDatetime;
	@Column(name="end_datetime",   nullable=false) private Instant endDatetime;

	@Enumerated(EnumType.STRING) @Column(nullable=false)
	private Status status = Status.CONFIRMED;

	@Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
}