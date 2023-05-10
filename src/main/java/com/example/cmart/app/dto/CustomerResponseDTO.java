package com.example.cmart.app.dto;

public class CustomerResponseDTO {

	private String email;
	private String accessToken;
	private String refreshToken;
	
	public CustomerResponseDTO() {
	}

	public CustomerResponseDTO(String email, String accessToken, String refreshToken) {
		this.email = email;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
