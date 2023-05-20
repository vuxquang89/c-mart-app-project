package com.example.cmart.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.example.cmart.app.converter.BookingConverter;
import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.converter.RatingConverter;
import com.example.cmart.app.dto.BookingDTO;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.dto.DriverDTO;
import com.example.cmart.app.dto.HistoryToPlacesDTO;
import com.example.cmart.app.dto.RatingRequestDTO;
import com.example.cmart.app.dto.ResponseDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.entity.RatingEntity;
import com.example.cmart.app.service.BookingService;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.service.RatingService;
import com.example.cmart.app.util.BookingStatus;
import com.example.cmart.app.util.DriverStatus;


@RestController
@RequestMapping("/api")
public class BookingAPI {

	@Autowired
	private DateTimeConverter dateTimeConvert;
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DriverConverter driverConvert;
	
	@Autowired
	private JwtTokenService jwtService;
	
	@Autowired
	private BookingConverter bookingConvert;
	
	@Autowired
	private DriverService driverService;

	
	@Autowired
	private BookingService bookingService;	
	
	@Autowired
	private RatingService ratingService;
	@Autowired
	private RatingConverter ratingConvert;
	
	/**
	 * sau khi nguoi dung chon diem den, se load ra danh sach cac xe o gan
	 * @param request nhan toa do bat dau va toa do muon den
	 * @return danh sach cac xe dang o gan vi tri nguoi dung dang dung
	 */
	
	@PostMapping("/customer/booking/cars")
	public ResponseEntity<?> getBookingDrivers(
			@RequestBody BookingRequestDTO requestDTO
			
			) {
		List<CarDTO> listCar = bookingService.getCarsInRadius(requestDTO);
		return ResponseEntity.ok(listCar);
	}
	
	/**
	 * thuc hien chon va dat xe
	 * @param requestDTO
	 * @return
	 */
	@PostMapping("/customer/booking")
	public ResponseEntity<BookingDTO> booking(@RequestBody BookingRequestDTO requestDTO,
			HttpServletRequest request){
		try {
			DriverEntity driverEntity = driverService.findByCarId(requestDTO.getCar().getId()).orElse(null);
			
			if(driverEntity != null) {
				
				String username = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
				CustomerEntity customer = customerService.findCustomerByEmail(username).get();
				
				CarEntity carEntity = driverEntity.getCar();
				BookingEntity bookingEntity = bookingConvert.toEntity(requestDTO);
				bookingEntity.setStatus(BookingStatus.waitting);
				bookingEntity.setCar(carEntity);
				bookingEntity.setCustomer(customer);
				
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
			System.out.println(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * khi khach hang chon di chuyen
	 * @return danh sach cac diem đã đến
	 */
	
	@GetMapping("/customer/move")
	public ResponseEntity<?> showHistoryToPlaces(HttpServletRequest request){
		String emailToken = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(emailToken).orElse(null);
		if(customer != null) {

			List<BookingEntity> listBooking = bookingService.findByCustomer(customer);
			List<HistoryToPlacesDTO> historyToPlaces = new ArrayList<HistoryToPlacesDTO>();
			for(BookingEntity book : listBooking) {
				historyToPlaces.add(bookingConvert.toHistoryDTO(book));
			}
			
			return ResponseEntity.ok(historyToPlaces);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	/**
	 * huy dat xe
	 * @param id
	 * @return
	 */
	
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
	
	/**
	 * xem lich su book xe
	 * @return danh sach cac chuyen di da book
	 */
	
	@GetMapping("/customer/booking/history")
	public ResponseEntity<?> getHistoryBooking(HttpServletRequest request){
		List<BookingDTO> historyBooking = new ArrayList<BookingDTO>();
		
		String emailToken = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(emailToken).orElse(null);
		if(customer != null) {
			List<BookingEntity> listBooking = bookingService.findByCustomer(customer);
			
			for(BookingEntity book : listBooking) {
				BookingDTO bookingDTO = bookingConvert.toDTO(book);
				DriverEntity driver = driverService.findByCarId(book.getCar().getId()).get();
				DriverDTO driverDTO = driverConvert.toDTO(driver);
				bookingDTO.setDriver(driverDTO);
				historyBooking.add(bookingDTO);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(historyBooking);
	}
	
	/**
	 * xoa lich su dat xe
	 * chỉ cho phép xóa các với trạng thái finish hoặc cancel
	 * @param id
	 */
	
	@DeleteMapping("/customer/booking/history/{id}")
	public void deleteBookingHistory(@PathVariable Long id) {
		bookingService.delete(id);
	}
	
	/**
	 * thuc hien danh gia
	 * @param id
	 * @param ratingRequest
	 * @return
	 */
	@PostMapping("/customer/booking/{id}/rating")
	public ResponseEntity<?> rating(@PathVariable Long id,
			@RequestBody RatingRequestDTO ratingRequest){
		BookingEntity booking = bookingService.findBookingFinishById(id).orElse(null); 
		if(booking != null) {
			DriverEntity driver = driverService.findByCarId(booking.getCar().getId()).get();
			RatingEntity rating = ratingConvert.toEntity(ratingRequest, booking, driver);
			driver.setRating(ratingConvert.toStar(ratingRequest, driver.getRating()));
			RatingRequestDTO response = ratingConvert.toDTO(ratingService.save(rating));
			return ResponseEntity.ok(response);
		}else {
			Map<String, String> mess = new HashMap<>();
			mess.put("warning", "The trip is not over yet");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
		}
	
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
