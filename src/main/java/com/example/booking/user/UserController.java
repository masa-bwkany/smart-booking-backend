package com.example.booking.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/users")
public class UserController {
	private final UserRepository users;
	public UserController(UserRepository users){ this.users = users; }

	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication auth){
		if (auth == null) return ResponseEntity.status(401).build();
		Long id = Long.parseLong((String) auth.getPrincipal());
		return users.findById(id)
				.<ResponseEntity<?>>map(u -> ResponseEntity.ok(new MeDto(u.getId(), u.getName(), u.getEmail(), u.getRole().name())))
				.orElseGet(() -> ResponseEntity.status(404).build());
	}
	public record MeDto(Long id, String name, String email, String role) {}
}