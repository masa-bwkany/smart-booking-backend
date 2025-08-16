package com.example.booking.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController @RequestMapping("/api/providers") @RequiredArgsConstructor
public class ProviderController {
	private final ProviderRepository repo;

	@GetMapping public List<Provider> list(){ return repo.findAll(); }

	@GetMapping("/{id}")
	public Provider one(@PathVariable Long id){
		return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	// NOTE: later protect by role=PROVIDER/ADMIN; for now it's open for development
	@PostMapping public Provider create(@RequestBody Provider p){ return repo.save(p); }
}