package com.example.booking.booking;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("""
  SELECT CASE WHEN COUNT(b)>0 THEN true ELSE false END
  FROM Booking b
  WHERE b.providerId = :providerId
    AND b.status = 'CONFIRMED'
    AND b.startDatetime < :end
    AND b.endDatetime   > :start
  """)
	boolean existsOverlap(@Param("providerId") Long providerId,
						  @Param("start") Instant start,
						  @Param("end") Instant end);
}