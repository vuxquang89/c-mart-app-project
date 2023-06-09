package com.example.cmart.app.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.example.cmart.app.converter.CustomerConverter;
import com.example.cmart.app.dto.CustomerInfoDTO;
import com.example.cmart.app.dto.CustomerPasswordDTO;
import com.example.cmart.app.dto.CustomerRegisterDTO;
import com.example.cmart.app.dto.CustomerRequestDTO;
import com.example.cmart.app.dto.CustomerResponseDTO;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.lib.Password;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.util.AuthProvider;
import com.example.cmart.app.util.TypeUser;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPI {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerConverter customerConvert;
	
	@Autowired
	private JwtTokenService jwtService;
    
	/**
	 * login với tài khoản đã đăng ký trên hệ thống
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid CustomerRequestDTO request){
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail()+","+TypeUser.CUSTOMER, request.getPassword()));
			
			CustomerEntity user = (CustomerEntity)authentication.getPrincipal();
			
			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			
			CustomerResponseDTO response = new CustomerResponseDTO(user.getEmail(), user.getUsername(), accessToken, refreshToken);
			
			return ResponseEntity.ok(response);
			
		}catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	/**
	 * lấy lại access token mới
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
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
					String username = jwtService.getUserNameFromJwtSubject(refreshToken);
					CustomerEntity user = customerService.findCustomerByEmail(username).get();
					String accessToken = jwtService.generateAccessToken(user);
	
					CustomerResponseDTO res = new CustomerResponseDTO(user.getEmail(), user.getUsername(), accessToken, refreshToken);
					return ResponseEntity.ok(res);
				}else {
					return ResponseEntity.ok().body("You need to login again!");
				}
				
				
			}catch(Exception ex) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
			}
			 
		}
	}
	
	/**
	 * người dùng đăng ký tài khoản mới
	 * @param customerDTO
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid CustomerRegisterDTO customerDTO){
		
		if(customerService.existsCustomerQuery(customerDTO.getEmail()) ||
				customerService.existsCustomerQuery(customerDTO.getUsername())) {
			Map<String, String> mess = new HashMap<>();
			mess.put("warning", "Email or Username is exists!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
		}
		
		CustomerEntity saveCustomer = customerConvert.toEntity(customerDTO);
		saveCustomer.setProvider(AuthProvider.local);
		saveCustomer.setRole("ROLE_USER");
		
		saveCustomer = customerService.save(saveCustomer);
		
		String accessToken = jwtService.generateAccessToken(saveCustomer);
		String refreshToken = jwtService.generateRefreshToken(saveCustomer);
		return ResponseEntity.ok(new CustomerResponseDTO(customerDTO.getEmail(),saveCustomer.getUsername(), accessToken, refreshToken));
		
	}
	
	/**
	 * hiển thị thông tin chi tiết tài khoản
	 * 
	 */
	@GetMapping("/info")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> showInfo(HttpServletRequest request){
		try {
			String emailToken = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
			
			CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
			customerInfoDTO = customerConvert.infoToDTO(customerService.findCustomerByEmail(emailToken).get());
			return ResponseEntity.ok(customerInfoDTO);
		}
		catch(Exception e) {
			System.out.println(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	/**
	 * sua thong tin ca nhan co ban
	 * @param info
	 * @return
	 */
	@PutMapping("/update/info")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> updateCustomerInfo(			
			@RequestBody @Valid CustomerInfoDTO info,
			HttpServletRequest request){
		
		String emailToken = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(emailToken).orElse(null);
		
		if(customer != null) {
			
			if(!customerService.existsCustomerQueryPhone(info.getPhone())) {
				CustomerInfoDTO infoDto = new CustomerInfoDTO();
				customer.setFullname(info.getFullname());
				customer.setPhone(info.getPhone());
				
				infoDto = customerConvert.infoToDTO(customerService.save(customer));
				return ResponseEntity.ok(infoDto);
			}else {
				Map<String, String> mess = new HashMap<>();
				mess.put("warning", "Phone number is exists!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
			}
			
		}else {
			System.out.println("email is not exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	/**
	 * cập nhật mật khẩu
	 * @param info
	 * @param request
	 * @return
	 */
	@PutMapping("/update/password")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> updateCustomerPassword(			
			@RequestBody @Valid CustomerPasswordDTO requestPass,
			HttpServletRequest request){
		
		String emailToken = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(emailToken).orElse(null);
		
		if(customer != null) {
			if(customer.getPassword() == null && customer.getProvider() == AuthProvider.google) {
				CustomerInfoDTO infoDto = new CustomerInfoDTO();				
				customer.setPassword(Password.encoderPassword(requestPass.getNewPassword()));
				infoDto = customerConvert.infoToDTO(customerService.save(customer));
				return ResponseEntity.ok(infoDto);
			}else if(Password.checkPaasword(requestPass.getOldPassword(), customer.getPassword())) {
				CustomerInfoDTO infoDto = new CustomerInfoDTO();				
				customer.setPassword(Password.encoderPassword(requestPass.getNewPassword()));
				infoDto = customerConvert.infoToDTO(customerService.save(customer));
				return ResponseEntity.ok(infoDto);
			}else {
				Map<String, String> mess = new HashMap<>();
				mess.put("warning", "Password is Incorrect!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
			}
					
			
		}else {
			System.out.println("email is not exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	
	
}
