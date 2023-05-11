package com.example.cmart.app.api;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.BookingConverter;
import com.example.cmart.app.converter.DistanceConverter;
import com.example.cmart.app.dto.BookingRequestDTO;
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

@RestController
@RequestMapping("/api")
public class BookingAPI {

	@Autowired
	private DistanceConverter convert;
	
	@Autowired
	private BookingConverter bookingConvert;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private BookingService bookingService;
	
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
	public ResponseEntity<?> booking(@RequestBody BookingRequestDTO requestDTO){
		DriverEntity driverEntity = driverService.findByCarId(requestDTO.getCar().getId()).get();
		CarEntity carEntity = driverEntity.getCar();
		BookingEntity bookingEntity = bookingConvert.toEntity(requestDTO);
		return null;
	}
	
	
	
	@GetMapping("/booking")
	public ResponseDTO getDriver(@RequestBody BookingRequestDTO request){
		List<DriverEntity> drivers = driverService.getDrivers(4, "waitting");
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
	
}
