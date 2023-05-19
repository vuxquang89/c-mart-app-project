package com.example.cmart.app.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.cmart.app.converter.CustomerConverter;
import com.example.cmart.app.dto.CustomerInfoDTO;
import com.example.cmart.app.dto.CustomerRegisterDTO;
import com.example.cmart.app.dto.CustomerRequestDTO;
import com.example.cmart.app.dto.CustomerResponseDTO;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.util.AuthProvider;

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
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
			CustomerEntity user = (CustomerEntity)authentication.getPrincipal();
			
			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			
			CustomerResponseDTO response = new CustomerResponseDTO(user.getEmail(), accessToken, refreshToken);
			
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
					Optional<CustomerEntity> user = customerService.findCustomer(username);
					String accessToken = jwtService.generateAccessToken(user.get());
	
					CustomerResponseDTO res = new CustomerResponseDTO(user.get().getEmail(), accessToken, refreshToken);
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
		return ResponseEntity.ok(new CustomerResponseDTO(customerDTO.getEmail(), accessToken, refreshToken));
		
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
			customerInfoDTO = customerConvert.infoToDTO(customerService.findCustomer(emailToken).get());
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
	@PutMapping("/{id}")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> updateCustomer(
			@PathVariable long id,
			@RequestBody @Valid CustomerInfoDTO info,
			HttpServletRequest request){
		
		String emailToken = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findById(id).orElse(null);
		
		CustomerInfoDTO infoDto = new CustomerInfoDTO();
		if(customer != null && emailToken.equals(info.getEmail())) {
			customer.setFullname(info.getFullname());
			customer.setPhone(info.getPhone());
			//customer.setProvider(AuthProvider.local);
			customer.setUsername(info.getUsername());
			
			infoDto = customerConvert.infoToDTO(customerService.save(customer));
		}
		
		return ResponseEntity.ok(infoDto);
	}
	
	
	
}
