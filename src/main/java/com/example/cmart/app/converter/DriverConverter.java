package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.DriverBookingResponseDTO;
import com.example.cmart.app.dto.DriverDTO;
import com.example.cmart.app.dto.DriverRegisterDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.lib.Password;
import com.example.cmart.app.util.BookingStatus;
import com.example.cmart.app.util.Gender;

@Component
public class DriverConverter {
	
	public CarDTO toCarDTO(DriverEntity driverEntity) {
		CarDTO carDTO = new CarDTO();
		carDTO.setId(driverEntity.getCar().getId());
		carDTO.setCarSeating(driverEntity.getCar().getCarSeating());
		carDTO.setCarType(driverEntity.getCar().getCarType());
		carDTO.setTotalPrace(0);
		
		return carDTO;
	}

	public DriverDTO toDTO(DriverEntity entity) {
		DriverDTO dto = new DriverDTO();
		if(entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setFullName(entity.getFullname());
		dto.setPhoneNumber(entity.getPhoneNumber());
		dto.setGender(entity.getGender().name());
		dto.setRating(entity.getRating());
		dto.setCurrentLat(entity.getCurrentLocationLat());
		dto.setCurrentLng(entity.getCurrentLocationLng());
		dto.setCarColor(entity.getCar().getCarColor());
		dto.setCarModel(entity.getCar().getCarModel());
		dto.setCarPlate(entity.getCar().getCarPlate());
		dto.setCarSeating(entity.getCar().getCarSeating());
		dto.setCarType(entity.getCar().getCarType());
		
		return dto;
	}
	
	public DriverBookingResponseDTO toDTOBooking(BookingEntity booking) {
		DriverBookingResponseDTO dto = new DriverBookingResponseDTO();
		if(booking.getId() != null) {
			dto.setId(booking.getId());
		}
		
		dto.setCustomerName(booking.getCustomer().getFullname());
		dto.setCustomerPhone(booking.getCustomer().getPhone());
		dto.setStartLocationLat(booking.getStartLocationLat());
		dto.setStartLocationLng(booking.getStartLocationLng());
		dto.setEndLocationLat(booking.getEndLocationLat());
		dto.setEndLocationLng(booking.getEndLocationLng());
		dto.setTotalFrice(booking.getTotalFare());
		dto.setStartTime(booking.getStartTime());
		dto.setEndTime(booking.getEndTime());
		dto.setStatus(BookingStatus.valueOf(booking.getStatus().name()).name());
		return dto;
	}
	
	public DriverEntity toEntity(DriverRegisterDTO dto) {
		DriverEntity entity = new DriverEntity();
		entity.setFullname(dto.getFullname());
		entity.setEmail(dto.getEmail());
		entity.setGender(Gender.valueOf(dto.getGender()));
		entity.setPhoneNumber(dto.getPhone());
		entity.setPassword(Password.encoderPassword(dto.getPassword()));
		entity.setUsername(dto.getUsername());
		return entity;
	}
}
