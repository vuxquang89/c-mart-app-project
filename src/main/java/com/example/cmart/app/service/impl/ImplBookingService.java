package com.example.cmart.app.service.impl;

import java.util.List;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.BookingEntity;

public interface ImplBookingService {
	
	List<CarDTO> getCarsInRadius(BookingRequestDTO request);
	
	BookingEntity save(BookingEntity bookingEntity);
}
