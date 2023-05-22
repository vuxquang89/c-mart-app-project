package com.example.cmart.app.dto;

public class DriverLoginRequestDTO {

	private String phoneNumber;
	private String password;
	private double currentLocationLat;
	private double currentLocationLng;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getCurrentLocationLat() {
		return currentLocationLat;
	}
	public void setCurrentLocationLat(double currentLocationLat) {
		this.currentLocationLat = currentLocationLat;
	}
	public double getCurrentLocationLng() {
		return currentLocationLng;
	}
	public void setCurrentLocationLng(double currentLocationLng) {
		this.currentLocationLng = currentLocationLng;
	}
	
}
