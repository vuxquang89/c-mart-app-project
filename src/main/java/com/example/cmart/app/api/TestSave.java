package com.example.cmart.app.api;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.BookingConverter;
import com.example.cmart.app.dto.BookingDTO;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.service.BookingService;
import com.example.cmart.app.service.DriverService;

@RestController
@RequestMapping("/save")
public class TestSave {
	
	@Autowired
	private BookingConverter bookingConvert;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private DriverService driverService;

	@PostMapping("/customer")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> booking(@RequestBody BookingRequestDTO requestDTO){
		BookingEntity bookingEntity = bookingConvert.toEntity(requestDTO);
		DriverEntity driverEntity = driverService.findByCarId(requestDTO.getCar().getId()).orElse(null);
		
		CarEntity carEntity = driverEntity.getCar();
		
		System.out.println("driver id :" + driverEntity.getId());
		System.out.println("car id : " + carEntity.getId());
		BookingEntity request = new BookingEntity();
		request.setStartLocationLat(15.9866010);
		request.setStartLocationLng(108.2021670);
	    request.setStartAddress("");
	    request.setEndLocationLat(16.0255941); 
	    request.setEndLocationLng(108.1664135);
	    
	    request.setEndAddress("Trung tâm Hội nghị&Tiệc cưới Minh Châu Việt");
	    request.setStartTime("2023-05-18 08:43:32");
	    request.setDistance(9400);
	    //request.set(840);
	    
	    CarEntity car = new CarEntity();
	    car.setId(1l);
	    car.setCarSeating(2);
	    car.setCarType("motobike");
	    //car.setTotalPrace(34569);
	    
	    //request.setCar(car);
	    
	    
	    request.setCar(carEntity);
	    bookingEntity.setCar(carEntity);
	    BookingEntity bookingDTO = bookingService.save(bookingEntity);
		
		return ResponseEntity.ok(bookingDTO.getId());
			
	}
	
}
