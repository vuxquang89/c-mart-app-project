package com.example.cmart.app.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.converter.ScheduledRideConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.ResponseDTO;
import com.example.cmart.app.dto.ScheduledRideDTO;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.entity.ScheduledRideEntity;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.service.ScheduledRideService;

@RestController
@RequestMapping("/api")
public class ScheduledRideBookAPI {

	@Autowired
	private ScheduledRideService rideService;
	
	@Autowired
	private ScheduledRideConverter sRideConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private DateTimeConverter dateTimeConvert;
	
	@PostMapping("/customer/scheduledride/booking/cars")
	public ResponseEntity<?> getCarScheduledRide(@RequestBody BookingRequestDTO requestDTO){
		List<CarDTO> listCar = rideService.getCarsInRadius(requestDTO);
		return ResponseEntity.ok(listCar);
	}
	
	@PostMapping("/customer/scheduledride/booking")
	public ResponseEntity<?> scheduledRideBook(@RequestBody BookingRequestDTO requestDTO){
		DriverEntity driverEntity = driverService.findByCarId(requestDTO.getCar().getId()).orElse(null);
		ResponseDTO<Object> response = new ResponseDTO<>();
		if(driverService != null) {
			CarEntity carEntity = driverEntity.getCar();
			
			//get customer
			CustomerEntity customer = new CustomerEntity();
			customer.setId(1l);
			
			if(rideService.findByCustomerId(customer.getId()).size() < 1) {
			
				ScheduledRideEntity sRideEntity = sRideConvert.toEntity(requestDTO);
				sRideEntity.setCar(carEntity);
				sRideEntity.setCustomer(null);
				sRideEntity.setBookingTime(dateTimeConvert.nowString());
				
				ScheduledRideDTO sRideDTO = sRideConvert.toDTO(rideService.save(sRideEntity));
				
				response.setResult_status("ok");
				response.setResult_mess("successful!");
				response.setResult(sRideDTO);
			}else {
				response.setResult_status("warning");
				response.setResult_mess("You cannot book more. You have an appointment waiting!");
			}
		}
		return ResponseEntity.ok(response);
	
	}
	
	
	//@PostMapping("/customer/scheduledride/booking/active")
}
