package com.example.booking.service;
import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

//@Entity @Table(name="services")
//@Getter @Setter @NoArgsConstructor
//public class ServiceEntity {
//	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
//	@Column(name="provider_id", nullable=false) private Long providerId;
//	@Column(nullable=false) private String name;
//	@Column(name="duration_min", nullable=false) private Integer durationMin;
//	@Column(precision = 10, scale = 2)
//	private Double price;
//	@Column(name="is_active", nullable=false) private Boolean active = true;
//}
//
//

// ServiceEntity.java
import java.math.BigDecimal;

@Entity @Table(name="services")
@Getter @Setter @NoArgsConstructor
public class ServiceEntity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	@Column(name="provider_id", nullable=false) private Long providerId;
	@Column(nullable=false) private String name;
	@Column(name="duration_min", nullable=false) private Integer durationMin;

	@Column(precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name="is_active", nullable=false) private Boolean active = true;
}