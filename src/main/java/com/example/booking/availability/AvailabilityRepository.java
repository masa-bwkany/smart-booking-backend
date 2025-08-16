package com.example.booking.availability;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
	List<Availability> findByProviderIdAndWeekday(Long providerId, Integer weekday);
}