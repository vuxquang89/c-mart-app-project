package com.example.cmart.app.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.CustomerResponseDTO;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.repository.CustomerRepository;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.util.AuthProvider;

@RestController
@RequestMapping("/")
public class GoogleAPI {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private JwtTokenService jwtUtil;

	/*
	@GetMapping()
	public ResponseEntity<CustomerResponseDTO> currentUser(@AuthenticationPrincipal OAuth2User user){
		return ResponseEntity.status(HttpStatus.OK).body(user.getAttribute("name"));
	}
	*/
	@GetMapping("api/google/success")
    public ResponseEntity<CustomerResponseDTO> success(OAuth2AuthenticationToken token) {
        //String accessToken = token.getAccessToken().getTokenValue();
        // use the access token to authenticate the user for other API calls		
		System.out.println(token.getPrincipal().getAttributes());
		OAuth2User oAuth2User = token.getPrincipal(); 
		String email = oAuth2User.getAttribute("email");
		System.out.println(email);
		String name = oAuth2User.getAttribute("given_name");
		CustomerEntity user = customerRepo.findByEmail(email).orElse(null); 
		
		
		if(user == null) {
			CustomerEntity userEntity = new CustomerEntity();
			userEntity.setEmail(email);
			userEntity.setFullname(name);
			userEntity.setUsername(getUsernameFormEmail(email));
			userEntity.setProvider(AuthProvider.google);
			userEntity.setRole("ROLE_USER");
			user = customerRepo.save(userEntity);
		}else {
			user.setProvider(AuthProvider.local);
		}
		String accessToken = jwtUtil.generateAccessToken(user);
		String refreshToken = jwtUtil.generateRefreshToken(user);
		
		
		CustomerResponseDTO response = new CustomerResponseDTO(user.getEmail(),user.getUsername(), accessToken, refreshToken);
		return ResponseEntity.ok(response);
    }
	
	private String getUsernameFormEmail(String email) {
		return email.split("@")[0];
	}
}
