package com.example.booking.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/availability") @RequiredArgsConstructor
public class AvailabilityController {
	private final AvailabilityRepository repo;

	@GetMapping("/provider/{providerId}/{weekday}")
	public List<Availability> forDay(@PathVariable Long providerId, @PathVariable Integer weekday){
		return repo.findByProviderIdAndWeekday(providerId, weekday);
	}

	@PostMapping public Availability create(@RequestBody Availability a){ return repo.save(a); }
}