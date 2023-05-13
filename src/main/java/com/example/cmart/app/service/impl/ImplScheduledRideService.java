package com.example.cmart.app.service.impl;

import java.util.List;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.ScheduledRideEntity;

public interface ImplScheduledRideService {

	List<CarDTO> getCarsInRadius(BookingRequestDTO request);
	
	List<ScheduledRideEntity> findByCustomerId(long id);
	
	ScheduledRideEntity save(ScheduledRideEntity sRideEntity);
}
