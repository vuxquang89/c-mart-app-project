package com.example.cmart.app.converter;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.util.BookingStatus;

@Component
public class BookingConverter {

	public BookingEntity toEntity(BookingRequestDTO requestDTO) {
		BookingEntity entity = new BookingEntity();
		entity.setStartLocationLat(requestDTO.getStartLat());
		entity.setStartLocationLng(requestDTO.getStartLng());
		entity.setEndLocationLat(requestDTO.getEndLat());
		entity.setEndLocationLng(requestDTO.getEndLng());
		entity.setStatus(BookingStatus.waitting);
		entity.setBookingTime(new Date());
		return entity;
	}
}
