package com.example.cmart.app.dto;

public class DriverResponseDTO {

	private String phoneNumber;
	private String username;
	private String accessToken;
	private String refreshToken;
	public DriverResponseDTO() {}
	
	public DriverResponseDTO(String phoneNumber, String username, String accessToken, String refreshToken) {
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
