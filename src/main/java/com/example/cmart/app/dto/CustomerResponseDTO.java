package com.example.cmart.app.dto;

public class CustomerResponseDTO {

	private String email;
	private String username;
	private String accessToken;
	private String refreshToken;
	
	public CustomerResponseDTO() {
	}

	public CustomerResponseDTO(String email, String username, String accessToken, String refreshToken) {
		this.email = email;
		this.username = username;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
}
