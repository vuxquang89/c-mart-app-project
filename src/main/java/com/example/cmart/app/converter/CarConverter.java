package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.CarEntity;
@Component
public class CarConverter {
	public CarEntity toEntity(CarDTO dto) {
		CarEntity entity = new CarEntity();
		entity.setCarColor(dto.getColor());
		entity.setCarModel(dto.getCarModel());
		entity.setCarType(dto.getCarType());
		entity.setCarSeating(dto.getCarSeating());
		entity.setCarPrice(dto.getTotalPrace());
		entity.setCarPlate(dto.getCarPlate());
		
		return entity;
	}
}
