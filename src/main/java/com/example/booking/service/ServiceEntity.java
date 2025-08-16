package com.example.booking.service;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="services")
@Getter @Setter @NoArgsConstructor
public class ServiceEntity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	@Column(name="provider_id", nullable=false) private Long providerId;
	@Column(nullable=false) private String name;
	@Column(name="duration_min", nullable=false) private Integer durationMin;
	private Double price;
	@Column(name="is_active", nullable=false) private Boolean active = true;
}