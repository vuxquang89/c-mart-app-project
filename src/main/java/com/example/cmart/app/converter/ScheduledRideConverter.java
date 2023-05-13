package com.example.cmart.app.converter;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.ScheduledRideDTO;
import com.example.cmart.app.entity.ScheduledRideEntity;

@Component
public class ScheduledRideConverter {

	public ScheduledRideEntity toEntity(BookingRequestDTO request) {
		ScheduledRideEntity entity = new ScheduledRideEntity();
		entity.setStartLocationLat(request.getStartLat());
		entity.setStartLocationLng(request.getStartLng());
		entity.setStartAddress(request.getStartAddress());

		entity.setEndLocationLat(request.getEndLat());
		entity.setEndLocationLng(request.getEndLng());
		entity.setEndAddress(request.getEndAddress());
		entity.setStartTime(request.getStartTime());
		entity.setTotalFare(0);
		//entity.setBookingTime(new Date());
		entity.setDistance(request.getDistanceTransfer());
		entity.setRideTime(request.getRideTime());
		return entity;
	}
	
	public ScheduledRideDTO toDTO(ScheduledRideEntity entity) {
		ScheduledRideDTO dto = new ScheduledRideDTO();
		
		dto.setId(entity.getId());
		dto.setStartLocationLat(entity.getStartLocationLat());
		dto.setStartLocationLng(entity.getStartLocationLng());
		dto.setStartAddress(entity.getStartAddress());

		dto.setEndLocationLat(entity.getEndLocationLat());
		dto.setEndLocationLng(entity.getEndLocationLng());
		dto.setEndAddress(entity.getEndAddress());
		dto.setStartTime(entity.getStartTime());
		dto.setTotalFare(entity.getTotalFare());
		dto.setBookingTime(entity.getBookingTime());
		dto.setDistance(entity.getDistance());
		dto.setRideTime(entity.getRideTime());
		
		CarDTO car = new CarDTO();
		car.setCarPlate(entity.getCar().getCarPlate());
		car.setCarSeating(entity.getCar().getCarSeating());
		car.setCarType(entity.getCar().getCarType());
		
		dto.setCar(car);
		
		return dto;
	}
}
