package com.example.cmart.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.service.ScheduledRideService;

@RestController
@RequestMapping("/")
public class ScheduledRideBookAPI {

	@Autowired
	private ScheduledRideService rideService;
	
	@PostMapping("/customer/scheduledride/booking/cars")
	public ResponseEntity<?> getCarScheduledRide(@RequestBody BookingRequestDTO requestDTO){
		List<CarDTO> listCar = rideService.getCarsInRadius(requestDTO);
		return ResponseEntity.ok(listCar);
	}
}
