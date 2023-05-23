package com.example.cmart.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.converter.ScheduledRideConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.ResponseDTO;
import com.example.cmart.app.dto.ScheduledRideDTO;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.entity.ScheduledRideEntity;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.service.ScheduledRideService;

@RestController
@RequestMapping("/api")
public class ScheduledRideBookAPI {

	@Autowired
	private ScheduledRideService rideService;
	
	@Autowired
	private ScheduledRideConverter sRideConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JwtTokenService jwtService;
	
	@Autowired
	private DateTimeConverter dateTimeConvert;
	
	@PostMapping("/customer/scheduledride/booking/cars")
	public ResponseEntity<?> getCarScheduledRide(@RequestBody BookingRequestDTO requestDTO){
		List<CarDTO> listCar = rideService.getCarsInRadius(requestDTO);
		return ResponseEntity.ok(listCar);
	}
	
	/**
	 * thêm mới lịch đặt xe
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@PostMapping("/customer/scheduledride/booking")
	public ResponseEntity<?> scheduledRideBook(@RequestBody BookingRequestDTO requestDTO,
			HttpServletRequest request){
		
		DriverEntity driverEntity = driverService.findByCarId(requestDTO.getCar().getId()).orElse(null);
		
		String username = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(username).get();
		
		ResponseDTO<Object> response = new ResponseDTO<>();
		if(driverService != null) {
			CarEntity carEntity = driverEntity.getCar();
			
			if(rideService.getByCustomerId(customer.getId()).size() < 1) {
			
				ScheduledRideEntity sRideEntity = sRideConvert.toEntity(requestDTO);
				sRideEntity.setCar(carEntity);
				sRideEntity.setCustomer(customer);
				sRideEntity.setBookingTime(dateTimeConvert.nowString());
				sRideEntity.setTotalFare(rideService.calTotalPrace(requestDTO.getStartLat(), 
						requestDTO.getStartLng(), 
						driverEntity.getCurrentLocationLat(), 
						driverEntity.getCurrentLocationLng(), 
						requestDTO.getDistanceTransfer(), 
						carEntity));
				
				ScheduledRideDTO sRideDTO = sRideConvert.toDTO(rideService.save(sRideEntity));
				
				response.setResult_status("ok");
				response.setResult_mess("successful!");
				response.setResult(sRideDTO);
			}else {
				response.setResult_status("warning");
				response.setResult_mess("You cannot book more. You have an appointment waiting!");
			}
		}
		return ResponseEntity.ok(response);
	
	}
	
	/**
	 * sủa đôi thông tin trong lịch đặt xe
	 * @param requestDTO
	 * @param request
	 * @return
	 */
	@PutMapping("/customer/scheduledride/booking/{id}")
	public ResponseEntity<?> updateScheduledRideBook(@PathVariable("id") long id,
			@RequestBody @Valid BookingRequestDTO requestDTO,
			HttpServletRequest request){
		
		ScheduledRideEntity sRideEntity = rideService.findById(id).orElse(null);
		
		ResponseDTO<Object> response = new ResponseDTO<>();
		if(sRideEntity != null) {
			DriverEntity driver = driverService.findByCarId(sRideEntity.getCar().getId()).orElse(null);
			sRideEntity.setEndAddress(requestDTO.getEndAddress());
			sRideEntity.setEndLocationLat(requestDTO.getEndLat());
			sRideEntity.setEndLocationLng(requestDTO.getEndLng());
			sRideEntity.setRideTime(requestDTO.getRideTime());
			sRideEntity.setDistance(requestDTO.getDistanceTransfer());
			sRideEntity.setTotalFare(rideService.calTotalPrace(requestDTO.getStartLat(), 
					requestDTO.getStartLng(), 
					driver.getCurrentLocationLat(), 
					driver.getCurrentLocationLng(), 
					requestDTO.getDistanceTransfer(), 
					sRideEntity.getCar()));
			ScheduledRideDTO sRideDTO = sRideConvert.toDTO(rideService.save(sRideEntity));
			
			response.setResult_status("ok");
			response.setResult_mess("successful!");
			response.setResult(sRideDTO);
		}
		return ResponseEntity.ok(response);
	
	}
	
	/**
	 * hủy chuyến đi đã đặt
	 * @return
	 */
	@DeleteMapping("/customer/scheduledride/booking/{id}")
	public ResponseEntity<?> deleteScheduledRide(@PathVariable("id") Long id,
			HttpServletRequest request){
		
		String username = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(username).get();
		
		try {
			Map<String, String> mess = new HashMap<>();
			ScheduledRideEntity scheduledRideEntity = rideService.findByCustomerId(id, customer.getId()).orElse(null);
			if(scheduledRideEntity != null) {
				rideService.delete(scheduledRideEntity);
				
				mess.put("success", "Successful delete");
				return ResponseEntity.status(HttpStatus.OK).body(mess);
			}else {
				mess.put("warning", "ScheduledRide not exist");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
			}
			
		}catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	/**
	 * lấy thông tin các chuyến xe book theo lịch của lái xe
	 * @param request
	 * @return
	 */
	@GetMapping("/driver/scheduledride/booking")
	public ResponseEntity<?> getScheduledRide(
			HttpServletRequest request){
		try {
			String driverPhone = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
			DriverEntity driver = driverService.findByPhoneNumber(driverPhone).orElse(null);
			if(driver != null) {
				List<ScheduledRideEntity> scheduledRides = rideService.getByCarId(driver.getCar().getId());
				List<ScheduledRideDTO> scheduledRideDTOs = new ArrayList<ScheduledRideDTO>();
				for(ScheduledRideEntity entity : scheduledRides) {
					ScheduledRideDTO dto = sRideConvert.toDTO(entity);
					scheduledRideDTOs.add(dto);
				}
				
				return ResponseEntity.ok(scheduledRideDTOs);
			}else {
				Map<String, String> mess = new HashMap<>();
				mess.put("warning", "Driver not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mess);
			}
		}catch(Exception ex) {
			System.out.println(ex.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
