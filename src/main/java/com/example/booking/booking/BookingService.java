package com.example.booking.booking;

import com.example.booking.availability.AvailabilityRepository;
import com.example.booking.service.ServiceEntity;
import com.example.booking.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookings;
	private final ServiceRepository services;
	private final AvailabilityRepository availability;

	public List<Instant> slots(Long providerId, Long serviceId, LocalDate date) {
		ServiceEntity svc = services.findById(serviceId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service"));

		int weekday = date.getDayOfWeek().getValue() % 7; // Monday=1..Sunday=7 -> 0..6
		var windows = availability.findByProviderIdAndWeekday(providerId, weekday);
		List<Instant> result = new ArrayList<>();

		ZoneId zone = ZoneId.systemDefault();
		for (var w : windows) {
			LocalDateTime cursor = LocalDateTime.of(date, w.getStartTime());
			LocalDateTime endWin = LocalDateTime.of(date, w.getEndTime());
			while (!cursor.plusMinutes(svc.getDurationMin()).isAfter(endWin)) {
				Instant start = cursor.atZone(zone).toInstant();
				Instant end = cursor.plusMinutes(svc.getDurationMin()).atZone(zone).toInstant();
				boolean overlap = bookings.existsOverlap(providerId, start, end);
				if (!overlap) result.add(start);
				cursor = cursor.plusMinutes(svc.getDurationMin());
			}
		}
		return result;
	}

	public Booking create(Long userId, Long providerId, Long serviceId, Instant start) {
		ServiceEntity svc = services.findById(serviceId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service"));
		Instant end = start.plus(Duration.ofMinutes(svc.getDurationMin()));

		// Check overlap
		if (bookings.existsOverlap(providerId, start, end))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "slot not available");

		Booking b = new Booking();
		b.setUserId(userId); b.setProviderId(providerId); b.setServiceId(serviceId);
		b.setStartDatetime(start); b.setEndDatetime(end);
		b.setStatus(Booking.Status.CONFIRMED);
		return bookings.save(b);
	}
}