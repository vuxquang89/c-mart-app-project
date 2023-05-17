package com.example.cmart.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.repository.BookingRepository;
import com.example.cmart.app.service.impl.ImplBookingService;
import com.example.cmart.app.util.AppConstants;
import com.example.cmart.app.util.BookingStatus;
import com.example.cmart.app.util.DriverStatus;

@Service
public class BookingService implements ImplBookingService{

	@Autowired
	private DistanceConverter distanceConvert;
	
	@Autowired
	private DriverConverter driverConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Override
	public List<CarDTO> getCarsInRadius(BookingRequestDTO request) {
		List<DriverEntity> drivers = driverService.getDrivers(AppConstants.RATING_ONE_STAR, DriverStatus.waitting);
		List<CarDTO> listBookingCar = new ArrayList<CarDTO>();
		double distance = 0;
		for(DriverEntity entity : drivers) {
			distance = getDistance(request.getStartLat(), 
					request.getStartLng(), 
					entity.getCurrentLocationLat(), 
					entity.getCurrentLocationLng());
			
			if(distance <= AppConstants.DEFAULT_RADIUS_LOCATION) { //500m
				//float totalPrace = (float)(request.getDistanceTransfer() * entity.getCar().getCarPrice() + 
				//		distance * entity.getCar().getCarPrice());
				float totalPrace = calPrace(distance, request.getDistanceTransfer(), entity.getCar().getCarPrice());
				
				CarDTO carDTO = driverConvert.toCarDTO(entity);
				carDTO.setTotalPrace(totalPrace);
				listBookingCar.add(carDTO);
			}
		}
		return listBookingCar;
	}
	
	@Override
	public BookingEntity save(BookingEntity bookingEntity) {		
		return bookingRepository.save(bookingEntity);
	}
	
	@Override
	public BookingEntity cancel(long id) {
		BookingEntity booking = bookingRepository.findById(id).orElse(null);
		if(booking != null) {
			booking.setStatus(BookingStatus.cancel);
			bookingRepository.save(booking);
		}
		return booking;
	}
	
	@Override
	public Optional<BookingEntity> findById(long id) {
		return bookingRepository.findById(id);
	}
	
	@Override
	public List<BookingEntity> findByCustomer(CustomerEntity customer) {
		return bookingRepository.findByCustomer(customer);
	}
	
	@Override
	public void delete(long id) {
		bookingRepository.deleteById(id);
	}
	
	public double getDistance(double startLat, double startLng, double currentLat, double currentLng) {
		return distanceConvert.getDistance(startLat, 
				startLng, 
				currentLat, 
				currentLng);
	}
	
	public float calPrace(double distance, float distanceTransfer, float carPrice) {
		return (float)(((int)distanceTransfer + (int)distance) * carPrice);
	}
	
	public float calTotalPrace(double startLat, double startLng, double currentLat, double currentLng,
			float distanceTransfer, CarEntity car) {
		double distance = getDistance(startLat, startLng, currentLat, currentLng);
		return calPrace(distance, distanceTransfer, car.getCarPrice());
	}
}
