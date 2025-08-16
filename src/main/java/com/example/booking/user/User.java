package com.example.booking.user;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="users")
@Getter @Setter @NoArgsConstructor
public class User {
	public enum Role { ADMIN, PROVIDER, CUSTOMER }
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	@Column(nullable=false) private String name;
	@Column(nullable=false, unique=true) private String email;
	@Column(name="password_hash", nullable=false) private String passwordHash;
	@Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
	@Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
}