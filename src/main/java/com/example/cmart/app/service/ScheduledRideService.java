package com.example.cmart.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.CarEntity;
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
	private DateTimeConverter dateTimeConvert;
	
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
				List<ScheduledRideEntity> scheduledRides = rideRepository.getByCarId(driverEntity.getCar().getId());
				if(scheduledRides.size() == 0) {
					System.out.println("size : " + scheduledRides.size());
					float totalPrace = calPrace(distance, request.getDistanceTransfer(), 
							driverEntity.getCar().getCarPrice());
					
					CarDTO carDTO = driverConvert.toCarDTO(driverEntity);
					carDTO.setTotalPrace(totalPrace);
					listBookingCar.add(carDTO);
				}else if(scheduledRides.size() < 2) {
					System.out.println("size : " + scheduledRides.size());
					for(ScheduledRideEntity scheduledRide : scheduledRides) {
						//int startTime_ScheduledRide = (int)scheduledRide.getStartTime().getTime()/1000;
	
						long startTime_ScheduledRide = dateTimeConvert.getSecondsOfDate(scheduledRide.getStartTime());
						
						//int startTime_Request = (int)request.getStartTime().getTime()/1000;
						long startTime_Request = dateTimeConvert.getSecondsOfDate(request.getStartTime());
						boolean check = false;
						System.out.println("scheduledRide.getStartTime() = " + scheduledRide.getStartTime());
						System.out.println("startTime_ScheduledRide: " + startTime_ScheduledRide);
						System.out.println("request.getStartTime() = " + request.getStartTime());
						System.out.println("startTime_Request " + startTime_Request);
						
						if(startTime_ScheduledRide > startTime_Request ) {
							long totalTime = startTime_Request + request.getRideTime();
							double distanceRide = distanceConvert.getDistance(request.getEndLat(), 
									request.getEndLng(), 
									scheduledRide.getStartLocationLat(), 
									scheduledRide.getStartLocationLng());
							if(startTime_ScheduledRide - totalTime >= AppConstants.HOUR_TO_SECONDS 
									&& distanceRide <= AppConstants.RADIUS_LOCATION_5000_M) {
								check = true;
							}
						}else if(startTime_ScheduledRide < startTime_Request) {
							long totalTime = startTime_ScheduledRide + scheduledRide.getRideTime();
							System.out.println("totalTime : " + totalTime);
							double distanceRide = distanceConvert.getDistance(
									scheduledRide.getEndLocationLat(), 
									scheduledRide.getEndLocationLng(),
									request.getStartLat(), 
									request.getStartLng()
									);
							System.out.println("distanceRide : " + distanceRide);
							if(startTime_Request - totalTime >= AppConstants.HOUR_TO_SECONDS 
									&& distanceRide <= AppConstants.RADIUS_LOCATION_5000_M) {
								check = true;
							}
						}
						
						System.out.println(check);
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
	
	@Override
	public List<ScheduledRideEntity> getByCarId(long id) {
		return rideRepository.getByCarId(id);
	}
	
	@Override
	public Optional<ScheduledRideEntity> findByCustomerId(Long id, Long customerId) {
		return rideRepository.findByCustomerId(id, customerId);
	}
	
	@Override
	public void delete(ScheduledRideEntity entity) {
		rideRepository.delete(entity);
	}
	
	@Override
	public List<ScheduledRideEntity> getByCustomerId(long id) {
		return rideRepository.getByCustomerId(id);
	}
	
	@Override
	public ScheduledRideEntity save(ScheduledRideEntity sRideEntity) {
		return rideRepository.save(sRideEntity);
	}
	
	@Override
	public Optional<ScheduledRideEntity> findById(Long id) {
		return rideRepository.findById(id);
	}
	
	public float calPrace(double distance, float distanceTransfer, float carPrice) {
		return (float)(((int)distanceTransfer + (int)distance) * carPrice);
	}
	
	public double getDistance(double startLat, double startLng, double currentLat, double currentLng) {
		return distanceConvert.getDistance(startLat, 
				startLng, 
				currentLat, 
				currentLng);
	}
	
	public float calTotalPrace(double startLat, double startLng, double currentLat, double currentLng,
			float distanceTransfer, CarEntity car) {
		double distance = getDistance(startLat, startLng, currentLat, currentLng);
		return calPrace(distance, distanceTransfer, car.getCarPrice());
	}
}
