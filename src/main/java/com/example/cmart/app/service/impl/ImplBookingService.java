package com.example.cmart.app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CustomerEntity;

public interface ImplBookingService {
	
	List<CarDTO> getCarsInRadius(BookingRequestDTO request);
	Optional<BookingEntity> findById(long id);
	Optional<BookingEntity> findBookingFinishById(long id);
	List<BookingEntity> findByCustomer(CustomerEntity customer);
	
	List<BookingEntity> findByDriverId(Long driverId);
	boolean checkBookingToStatus(String status, Long id);
	
	BookingEntity save(BookingEntity bookingEntity);
	BookingEntity cancel(long id);
	
	void delete(long id);
}
