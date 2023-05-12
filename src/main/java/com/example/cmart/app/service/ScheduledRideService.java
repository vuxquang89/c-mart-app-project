package com.example.cmart.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.entity.ScheduledRideEntity;
import com.example.cmart.app.repository.ScheduledRideRepository;
import com.example.cmart.app.service.impl.ImplScheduledRideService;
import com.example.cmart.app.util.AppConstants;
import com.example.cmart.app.util.DriverStatus;

@Service
public class ScheduledRideService implements ImplScheduledRideService{

	@Autowired
	private DistanceConverter distanceConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private DriverConverter driverConvert;
	
	@Autowired
	private ScheduledRideRepository rideRepository;
	
	@Override
	public List<CarDTO> getCarsInRadius(BookingRequestDTO request) {
		List<DriverEntity> drivers = driverService.getDrivers(AppConstants.RATING_ONE_STAR, DriverStatus.waitting);
		List<CarDTO> listBookingCar = new ArrayList<CarDTO>();
		double distance = 0;
		for(DriverEntity driverEntity : drivers) {
			distance = distanceConvert.getDistance(request.getStartLat(), 
					request.getStartLng(), 
					driverEntity.getCurrentLocationLat(), 
					driverEntity.getCurrentLocationLng());
			
			if(distance <= AppConstants.DEFAULT_RADIUS_LOCATION) { //500m
				//float totalPrace = (float)(request.getDistanceTransfer() * entity.getCar().getCarPrice() + 
				//		distance * entity.getCar().getCarPrice());
				List<ScheduledRideEntity> scheduledRides = rideRepository.findByCarId(driverEntity.getCar().getId());
				if(scheduledRides.size() < 2) {
					for(ScheduledRideEntity scheduledRide : scheduledRides) {
						int startTime_ScheduledRide = (int)scheduledRide.getStartTime().getTime()/1000;
						int startTime_Request = (int)request.getStartTime().getTime()/1000;
						boolean check = false;
						
						if(startTime_ScheduledRide > startTime_Request ) {
							int totalTime = startTime_Request + request.getRideTime();
							double distanceRide = distanceConvert.getDistance(request.getEndLat(), 
									request.getEndLng(), 
									scheduledRide.getStartLocationLat(), 
									scheduledRide.getStartLocationLng());
							if(startTime_ScheduledRide - totalTime >= AppConstants.HOUR_TO_SECONDS 
									&& distanceRide <= AppConstants.RADIUS_LOCATION_5000_M) {
								check = true;
							}
						}else if(startTime_ScheduledRide < startTime_Request) {
							int totalTime = startTime_ScheduledRide + scheduledRide.getRideTime();
							double distanceRide = distanceConvert.getDistance(
									scheduledRide.getEndLocationLat(), 
									scheduledRide.getEndLocationLng(),
									request.getStartLat(), 
									request.getStartLng()
									);
							if(startTime_Request - totalTime >= AppConstants.HOUR_TO_SECONDS 
									&& distanceRide <= AppConstants.RADIUS_LOCATION_5000_M) {
								check = true;
							}
						}
						
						if(check) {
							//float totalPrace = (float)(request.getDistanceTransfer() * entity.getCar().getCarPrice() + 
							//		distance * entity.getCar().getCarPrice());
							float totalPrace = calPrace(distance, request.getDistanceTransfer(), 
									driverEntity.getCar().getCarPrice());
							
							CarDTO carDTO = driverConvert.toCarDTO(driverEntity);
							carDTO.setTotalPrace(totalPrace);
							listBookingCar.add(carDTO);
						}
					}
					
				}
			}
		}
		return listBookingCar;
	}
	
	public float calPrace(double distance, float distanceTransfer, float carPrice) {
		return (float)(((int)distanceTransfer + (int)distance) * carPrice);
	}
}
