package com.example.cmart.app.service.impl;

import java.util.List;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;

public interface ImplScheduledRideService {

	List<CarDTO> getCarsInRadius(BookingRequestDTO request);
}
