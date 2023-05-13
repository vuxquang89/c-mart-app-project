package com.example.cmart.app.dto;

public class CarDTO {

	private Long id;
	private String carType;
	private int carSeating;
	private String carPlate;
	private float totalPrace;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public int getCarSeating() {
		return carSeating;
	}
	public void setCarSeating(int carSeating) {
		this.carSeating = carSeating;
	}
	public float getTotalPrace() {
		return totalPrace;
	}
	public void setTotalPrace(float toltalPrace) {
		this.totalPrace = toltalPrace;
	}
	
	
}
