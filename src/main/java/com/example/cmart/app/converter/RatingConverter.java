package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.RatingRequestDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.entity.RatingEntity;

@Component
public class RatingConverter {

	public RatingEntity toEntity(RatingRequestDTO dto, BookingEntity booking, DriverEntity driver) {
		RatingEntity entity = new RatingEntity();
		
		entity.setBooking(booking);
		entity.setDriver(driver);
		entity.setComment(dto.getComment());
		entity.setEfficiency(dto.getEffective());
		entity.setOperatingSpeed(dto.getOperatingSpeed());
		entity.setServiceAttitude(dto.getServiceAttitude());
		entity.setSafety(dto.getSafe());
		
		return entity;
	}
	
	public RatingRequestDTO toDTO(RatingEntity entity) {
		RatingRequestDTO dto = new RatingRequestDTO();
		dto.setId(entity.getId());
		dto.setComment(entity.getComment());
		dto.setEffective(entity.getEfficiency());
		dto.setOperatingSpeed(entity.getOperatingSpeed());
		dto.setSafe(entity.getSafety());
		dto.setServiceAttitude(entity.getServiceAttitude());
		return dto;
	}
	
	public float toStar(RatingRequestDTO dto, float rating) {
		float averaged = (dto.getEffective() + dto.getOperatingSpeed() + dto.getServiceAttitude() + dto.getSafe())/4;
		return Math.round((averaged + rating)/2 * 10) / 10;
		
	}
}
