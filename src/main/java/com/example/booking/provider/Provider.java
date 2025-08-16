package com.example.booking.provider;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "providers")
@Getter @Setter @NoArgsConstructor
public class Provider {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	@Column(name="user_id", nullable=false) private Long userId;      // owner user
	@Column(name="display_name", nullable=false) private String displayName;
	private String location;
	private String phone;
}