package com.example.cmart.app.dto;

public class DriverDTO extends AbstractDTO<DriverDTO>{

	//private Long id;
	private String fullName;
	private String phoneNumber;
	private String gender;
	private float rating;
	private double currentLat;
	private double currentLng;
	private String carColor;
	private String carModel;
	private String carPlate;
	private int carSeating;
	private String carType;

	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public double getCurrentLat() {
		return currentLat;
	}
	public void setCurrentLat(double currentLat) {
		this.currentLat = currentLat;
	}
	public double getCurrentLng() {
		return currentLng;
	}
	public void setCurrentLng(double currentLng) {
		this.currentLng = currentLng;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public int getCarSeating() {
		return carSeating;
	}
	public void setCarSeating(int carSeating) {
		this.carSeating = carSeating;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	
}
