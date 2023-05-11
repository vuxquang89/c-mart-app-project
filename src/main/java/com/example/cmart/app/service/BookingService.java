package com.example.cmart.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.service.impl.ImplBookingService;
import com.example.cmart.app.util.AppConstants;

@Service
public class BookingService implements ImplBookingService{

	@Autowired
	private DistanceConverter distanceConvert;
	
	@Autowired
	private DriverConverter driverConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private BookingService bookingService;
	
	@Override
	public List<CarDTO> getCarsInRadius(BookingRequestDTO request) {
		List<DriverEntity> drivers = driverService.getDrivers(3, "waitting");
		List<CarDTO> listBookingCar = new ArrayList<CarDTO>();
		double distance = 0;
		for(DriverEntity entity : drivers) {
			distance = distanceConvert.getDistance(request.getStartLat(), 
					request.getStartLng(), 
					entity.getCurrentLocationLat(), 
					entity.getCurrentLocationLng());
			
			if(distance <= AppConstants.DEFAULT_RADIUS_LOCATION) { //500m
				float totalPrace = (float)(request.getDistanceTransfer() * entity.getCar().getCarPrice() + 
						distance * entity.getCar().getCarPrice());
				CarDTO carDTO = driverConvert.toCarDTO(entity);
				carDTO.setTotalPrace(totalPrace);
				listBookingCar.add(carDTO);
			}
		}
		return listBookingCar;
	}
	
	@Override
	public BookingEntity save(BookingEntity bookingEntity) {		
		return bookingService.save(bookingEntity);
	}
}
