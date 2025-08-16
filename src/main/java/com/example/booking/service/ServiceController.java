package com.example.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController @RequestMapping("/api/services") @RequiredArgsConstructor
public class ServiceController {
	private final ServiceRepository services;

	@GetMapping("/{id}") public ServiceEntity one(@PathVariable Long id){
		return services.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/by-provider/{providerId}") public List<ServiceEntity> byProvider(@PathVariable Long providerId){
		return services.findByProviderId(providerId);
	}

	@PostMapping public ServiceEntity create(@RequestBody ServiceEntity s){ return services.save(s); }
}