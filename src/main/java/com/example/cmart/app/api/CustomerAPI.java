package com.example.cmart.app.api;

import java.io.IOException;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

@RestController
@RequestMapping("/")
public class CustomerAPI {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@GetMapping
	public Map<String, Object> currentUser(OAuth2AuthenticationToken token){
		return token.getPrincipal().getAttributes();
	}
	
	@GetMapping("success")
    public Map<String, Object> success(OAuth2AuthenticationToken token) {
        //String accessToken = token.getAccessToken().getTokenValue();
        // use the access token to authenticate the user for other API calls
		System.out.println("chay cai nay");
		System.out.println(token.getPrincipal().getAttributes());
		return token.getPrincipal().getAttributes();
    }
	
	
	@GetMapping("api/login/google/callback")
    public String home(@AuthenticationPrincipal OAuth2User user) {
        String name = user.getAttribute("name");
        String email = user.getAttribute("email");
        
        return name;
    }
    
	
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
	
	
	
}
