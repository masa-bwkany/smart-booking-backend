package com.example.booking.availability;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity @Table(name="availability")
@Getter @Setter @NoArgsConstructor
public class Availability {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	@Column(name="provider_id", nullable=false) private Long providerId;
	@Column(nullable=false) private Integer weekday; // 0..6  (0=Sunday)
	@Column(name="start_time", nullable=false) private LocalTime startTime;
	@Column(name="end_time",   nullable=false) private LocalTime endTime;
}