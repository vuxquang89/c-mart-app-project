package com.example.cmart.app.api;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.BookingConverter;
import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.dto.BookingDTO;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.BookingResponseDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.DriverDTO;
import com.example.cmart.app.dto.ResponseDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.service.BookingService;
import com.example.cmart.app.service.CarService;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.util.AppConstants;
import com.example.cmart.app.util.BookingStatus;
import com.example.cmart.app.util.DriverStatus;

import antlr.DocBookCodeGenerator;

@RestController
@RequestMapping("/api")
public class BookingAPI {

	@Autowired
	private DistanceConverter convert;
	
	@Autowired
	private BookingConverter bookingConvert;
	
	@Autowired
	private DriverConverter driverConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private DateTimeConverter dateTimeConvert;
	
	@Autowired
	private CarService carService;
	
	/**
	 * sau khi nguoi dung chon diem den, se load ra danh sach cac xe o gan
	 * @param request nhan toa do bat dau va toa do muon den
	 * @return danh sach cac xe dang o gan vi tri nguoi dung dang dung
	 */
	@PostMapping("/customer/booking/cars")
	public ResponseEntity<?> getBookingDrivers(@RequestBody BookingRequestDTO requestDTO) {
		List<CarDTO> listCar = bookingService.getCarsInRadius(requestDTO);
		return ResponseEntity.ok(listCar);
	}
	
	/**
	 * thuc hien dat xe
	 * @param requestDTO
	 * @return
	 */
	@PostMapping("/customer/booking")
	public ResponseEntity<BookingDTO> booking(@RequestBody BookingRequestDTO requestDTO){
		try {
			DriverEntity driverEntity = driverService.findByCarId(requestDTO.getCar().getId()).orElse(null);
			if(driverEntity != null) {
				CarEntity carEntity = driverEntity.getCar();
				BookingEntity bookingEntity = bookingConvert.toEntity(requestDTO);
				bookingEntity.setStatus(BookingStatus.waitting);
				bookingEntity.setCar(carEntity);
				bookingEntity.setCustomer(null);
				
				float totalPrace = bookingService.calTotalPrace(requestDTO.getStartLat(),
						requestDTO.getStartLng(), driverEntity.getCurrentLocationLat(),
						driverEntity.getCurrentLocationLng(), requestDTO.getDistanceTransfer(), carEntity);
				
				bookingEntity.setTotalFare(totalPrace);
				bookingEntity.setStartTime(dateTimeConvert.nowString());
				BookingDTO bookingDTO = bookingConvert.toDTO(bookingService.save(bookingEntity));
								
				driverEntity.setStatus(DriverStatus.receive);
				driverEntity = driverService.save(driverEntity);
				DriverDTO driver = driverConvert.toDTO(driverEntity);
				bookingDTO.setDriver(driver);
				
				return ResponseEntity.ok(bookingDTO);
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}catch(Exception ex) {
			//return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/customer/booking/cancel/{id}")
	public ResponseEntity<?> cancelBooking(@PathVariable("id") long id){
		BookingEntity bookingEntity = bookingService.cancel(id);
		
		DriverEntity driver = driverService.findByCarId(bookingEntity.getCar().getId()).orElse(null);
		if(driver != null) {
			driver.setStatus(DriverStatus.waitting);
			driverService.save(driver);
		}
		BookingDTO bookingDTO = bookingConvert.toDTO(bookingEntity);
		return ResponseEntity.ok(bookingDTO);
	}
	
	@DeleteMapping("/customer/booking/delete/{id}")
	public void deleteBooking(@PathVariable("id") long id){
		bookingService.delete(id);
	}
	
	
	/*
	@GetMapping("/booking")
	public ResponseDTO getDriver(@RequestBody BookingRequestDTO request){
		List<DriverEntity> drivers = driverService.getDrivers(AppConstants.RATING_ONE_STAR, DriverStatus.waitting);
		List<DriverEntity> bookingDriver = new ArrayList<DriverEntity>();
		for(DriverEntity entity : drivers) {
			if(convert.getDistance(request.getStartLat(), 
					request.getStartLng(), 
					entity.getCurrentLocationLat(), 
					entity.getCurrentLocationLng()) <= AppConstants.DEFAULT_RADIUS_LOCATION) { //500m
				bookingDriver.add(entity);
			}
		}
		ResponseDTO response = new ResponseDTO();
		response.setListDriver(bookingDriver);
		return response;
	}
	*/
}
