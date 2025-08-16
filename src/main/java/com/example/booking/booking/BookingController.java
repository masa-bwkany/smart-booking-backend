package com.example.booking.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/booking") @RequiredArgsConstructor
public class BookingController {
	private final BookingService service;

	// GET available slots for a date
	@GetMapping("/slots")
	public List<Instant> slots(@RequestParam Long providerId,
							   @RequestParam Long serviceId,
							   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return service.slots(providerId, serviceId, date);
	}

	// Create a booking at an exact start time (ISO-8601 instant)
	@PostMapping
	public Booking create(Authentication auth, @RequestBody Map<String,String> body) {
		if (auth == null || !auth.isAuthenticated())
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthenticated");

		Long userId;
		try {
			userId = Long.parseLong(auth.getName()); // 👈 use getName(), not getPrincipal()
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid principal");
		}

		Long providerId = Long.valueOf(body.get("providerId"));
		Long serviceId  = Long.valueOf(body.get("serviceId"));
		Instant start   = Instant.parse(body.get("start"));
		return service.create(userId, providerId, serviceId, start);
	}
}