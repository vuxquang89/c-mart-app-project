package com.example.cmart.app.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.ResponseDTO;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.service.DriverService;

@RestController
@RequestMapping("/api")
public class BookingAPI {

	@Autowired
	private DistanceConverter convert;
	@Autowired
	private DriverService driverService;
	
	@GetMapping("/booking")
	public ResponseDTO getDriver(@RequestBody BookingRequestDTO request){
		List<DriverEntity> drivers = driverService.getDrivers(4, "waitting");
		List<DriverEntity> bookingDriver = new ArrayList<DriverEntity>();
		for(DriverEntity entity : drivers) {
			if(convert.getDistance(request.getLat(), request.getLng(), entity.getCurrentLocationLat(), entity.getCurrentLocationLng()) <= 500) {
				bookingDriver.add(entity);
			}
		}
		ResponseDTO response = new ResponseDTO();
		response.setListDriver(bookingDriver);
		return response;
	}
	
}
