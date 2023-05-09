package com.example.cmart.app.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class CustomerAPI {

	
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
    
	
	
	
}
