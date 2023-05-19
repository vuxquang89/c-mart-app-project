package com.example.cmart.app.converter;


import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.BookingDTO;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.HistoryToPlacesDTO;
import com.example.cmart.app.entity.BookingEntity;

@Component
public class BookingConverter {

	public BookingEntity toEntity(BookingRequestDTO requestDTO) {
		BookingEntity entity = new BookingEntity();
		entity.setStartLocationLat(requestDTO.getStartLat());
		entity.setStartLocationLng(requestDTO.getStartLng());
		entity.setStartAddress(requestDTO.getStartAddress());
		entity.setEndLocationLat(requestDTO.getEndLat());
		entity.setEndLocationLng(requestDTO.getEndLng());
		System.out.println(requestDTO.getEndAddress());
		entity.setEndAddress(requestDTO.getEndAddress());
		//entity.setStatus(BookingStatus.waitting);
		//entity.setBookingTime(new Date());
		entity.setDistance(requestDTO.getDistanceTransfer());
		entity.setTotalFare(requestDTO.getCar().getTotalPrace());
		return entity;
	}
	
	public BookingEntity toEntity(BookingRequestDTO requestDTO, BookingEntity entity) {
		
		entity.setStartLocationLat(requestDTO.getStartLat());
		entity.setStartLocationLng(requestDTO.getStartLng());
		entity.setStartAddress(requestDTO.getStartAddress());
		entity.setEndLocationLat(requestDTO.getEndLat());
		entity.setEndLocationLng(requestDTO.getEndLng());
		System.out.println(requestDTO.getEndAddress());
		entity.setEndAddress(requestDTO.getEndAddress());
		//entity.setStatus(BookingStatus.waitting);
		//entity.setBookingTime(new Date());
		entity.setDistance(requestDTO.getDistanceTransfer());
		entity.setTotalFare(requestDTO.getCar().getTotalPrace());
		return entity;
	}
	
	public BookingDTO toDTO(BookingEntity entity) {
		
		BookingDTO dto = new BookingDTO();
		if(entity.getId() != null) {
			dto.setId(entity.getId());
			dto.setStartLat(entity.getStartLocationLat());
			dto.setStartLng(entity.getStartLocationLng());
			dto.setStartAddress(entity.getStartAddress());
			dto.setEndLat(entity.getEndLocationLat());
			dto.setEndLng(entity.getEndLocationLng());
			dto.setEndAddress(entity.getEndAddress());
			dto.setStartTime(entity.getStartTime());
			dto.setEndTime(entity.getEndTime());
			dto.setDistanceTransfer(entity.getDistance());
			
			dto.setTotalFare(entity.getTotalFare());
			dto.setPaymentMethod(entity.getPaymentMethod());
		}
		return dto;
	}
	
	public HistoryToPlacesDTO toHistoryDTO(BookingEntity entity) {
		HistoryToPlacesDTO dto = new HistoryToPlacesDTO();
		dto.setLat(entity.getEndLocationLat());
		dto.setLng(entity.getEndLocationLng());
		dto.setAddress(entity.getEndAddress());
		return dto;
	}
}
