package com.example.cmart.app.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.cmart.app.dto.CustomerRequestDTO;
import com.example.cmart.app.dto.CustomerResponseDTO;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.jwt.JwtTokenUtil;
import com.example.cmart.app.service.CustomerService;

@RestController
@RequestMapping("/")
public class CustomerAPI {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
    
	
	@PostMapping("api/login")
	public ResponseEntity<?> login(@RequestBody @Valid CustomerRequestDTO request){
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			
			CustomerEntity user = (CustomerEntity)authentication.getPrincipal();
			
			String accessToken = jwtUtil.generateAccessToken(user);
			String refreshToken = jwtUtil.generateRefreshToken(user);
			
			//revokeAllUserTokens(user);
			//saveUserToken(user, accessToken, refreshToken);
			
			CustomerResponseDTO response = new CustomerResponseDTO(user.getEmail(), accessToken, refreshToken);
			
			return ResponseEntity.ok(response);
			
		}catch(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	@GetMapping("api/token/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String header = request.getHeader("Authorization");
		System.out.println("refresh token " + header);
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			throw new RuntimeException("Refresh token is missing");
		}else {
			try {
				String refreshToken = header.split(" ")[1].trim();
				
				if(jwtUtil.validateToken(refreshToken, response)) {
					String username = jwtUtil.getUserNameFromJwtSubject(refreshToken);
					Optional<CustomerEntity> user = customerService.findCustomer(username);
					String accessToken = jwtUtil.generateAccessToken(user.get());
					//UserResponseDTO res = new UserResponseDTO(user.get().getUsername(), accessToken, refreshToken);
					CustomerResponseDTO res = new CustomerResponseDTO(user.get().getEmail(), accessToken, refreshToken);
					return ResponseEntity.ok(res);
				}else {
					return ResponseEntity.ok().body("You need to login again!");
				}
				
				
			}catch(Exception ex) {
				/*
				Map<String, String> error = new HashMap<String, String>();
				
				error.put("error_message", ex.getMessage());
				new ObjectMapper().writeValue(response.getOutputStream(), error);
				*/
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
			}
			 
		}
	}
	
	/*
	private void revokeAllUserTokens(UserEntity user) {
		List<TokenEntity> tokens = tokenService.findAllTokensByUser(user.getId());
		if(tokens.isEmpty())
			return;
		tokens.forEach(t ->{
			t.setExpired(true);
			t.setRevoked(true);
		});
		tokenService.saveAll(tokens);
	}
	*/
}
