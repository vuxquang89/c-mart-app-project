package com.example.cmart.app.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.CarConverter;
import com.example.cmart.app.converter.DriverConverter;
import com.example.cmart.app.dto.CustomerResponseDTO;
import com.example.cmart.app.dto.DriverDTO;
import com.example.cmart.app.dto.DriverLoginRequestDTO;
import com.example.cmart.app.dto.DriverRegisterDTO;
import com.example.cmart.app.dto.DriverResponseDTO;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.service.CarService;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.util.DriverStatus;
import com.example.cmart.app.util.TypeUser;


@RestController
@RequestMapping("/api/driver")
public class DriverAPI {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenService jwtService;
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private DriverConverter driverConvert;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CarConverter carConvert;

	/**
	 * lái xe đăng nhập
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid DriverLoginRequestDTO request){
		try {
			
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getPhoneNumber()+","+TypeUser.DRIVER, request.getPassword()));
			
			DriverEntity user = (DriverEntity)authentication.getPrincipal();
			
			if(user.getStatus().equals(DriverStatus.off)) {
				user.setStatus(DriverStatus.waitting);
			}
			
			user.setCurrentLocationLat(request.getCurrentLocationLat());
			user.setCurrentLocationLng(request.getCurrentLocationLng());
			
			user = driverService.save(user);
			
			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			
			DriverResponseDTO response = new DriverResponseDTO(user.getPhoneNumber(), user.getUsername(), accessToken, refreshToken);
			
			return ResponseEntity.ok(response);
			
		}catch(BadCredentialsException ex) {
			System.out.println(ex.toString());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	@GetMapping("/token/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String header = request.getHeader("Authorization");
		System.out.println("refresh token " + header);
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			throw new RuntimeException("Refresh token is missing");
		}else {
			try {
				String refreshToken = header.split(" ")[1].trim();
				
				if(jwtService.validateToken(refreshToken, response)) {
					String phone = jwtService.getUserNameFromJwtSubject(refreshToken);
					DriverEntity user = driverService.findByPhoneNumber(phone).get();
					String accessToken = jwtService.generateAccessToken(user);
	
					CustomerResponseDTO res = new CustomerResponseDTO(user.getPhoneNumber(), user.getUsername(), accessToken, refreshToken);
					return ResponseEntity.ok(res);
				}else {
					return ResponseEntity.ok().body("You need to login again!");
				}
				
				
			}catch(Exception ex) {
				System.out.println(ex.toString());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
			}
			 
		}
	}
	
	/**
	 * đăng ký tài khoản cho lái xe
	 * @param driverDTO
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid DriverRegisterDTO driverDTO){
		boolean check = false;
		Map<String, String> mess = new HashMap<>();
		
		if(driverService.existsByEmail(driverDTO.getEmail())) {
			mess.put("warning", "Email is exists!");
			check = true;
		}else if(driverService.existsByPhone(driverDTO.getPhone())) {
			mess.put("warning", "Phone number is exists!");
			check = true;
		}else if(driverService.existsByUsename(driverDTO.getUsername())) {
			mess.put("warning", "Username is exists!");
			check = true;
		}else if(carService.existsByCarPlate(driverDTO.getCar().getCarPlate())) {
			mess.put("warning", "Car plate is exists!");
			check = true;
		}
		
		if(check) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
		}
		
		DriverEntity driver = driverConvert.toEntity(driverDTO);
		CarEntity carEntity = carConvert.toEntity(driverDTO.getCar());
		
		driver.setCar(carEntity);
		driver.setRole("ROLE_DRIVER");
		driver.setStatus(DriverStatus.off);
		driver.setRating(5f);
		driver = driverService.save(driver);
		
		DriverDTO driverResponse = driverConvert.toDTO(driver);
			
		return ResponseEntity.ok(driverResponse);
	}
	 
}
