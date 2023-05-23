package com.example.cmart.app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.ScheduledRideEntity;

public interface ImplScheduledRideService {

	List<CarDTO> getCarsInRadius(BookingRequestDTO request);
	
	List<ScheduledRideEntity> getByCustomerId(long id);
	List<ScheduledRideEntity> getByCarId(long id);

	Optional<ScheduledRideEntity> findById(Long id);
	Optional<ScheduledRideEntity> findByCustomerId(Long id, Long customerId);
	
	void delete(ScheduledRideEntity entity);
	
	ScheduledRideEntity save(ScheduledRideEntity sRideEntity);
}
