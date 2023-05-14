package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.DriverDTO;
import com.example.cmart.app.entity.DriverEntity;

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
}