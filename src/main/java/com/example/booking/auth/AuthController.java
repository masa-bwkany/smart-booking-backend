package com.example.booking.auth;

import com.example.booking.auth.dto.*;
import com.example.booking.user.User;
import com.example.booking.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController @RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserRepository users;
	private final PasswordEncoder encoder;
	private final JwtService jwt;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest req) {
		if (users.findByEmail(req.email()).isPresent())
			throw new ResponseStatusException(HttpStatus.CONFLICT,"Email exists");
		User u = new User();
		u.setName(req.name()); u.setEmail(req.email());
		u.setPasswordHash(encoder.encode(req.password()));
		try { u.setRole(User.Role.valueOf(req.role().toUpperCase())); }
		catch (IllegalArgumentException e){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid role"); }
		users.save(u);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
		var u = users.findByEmail(req.email())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		if (!encoder.matches(req.password(), u.getPasswordHash()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		return ResponseEntity.ok(new AuthResponse(jwt.generate(u)));
	}
}