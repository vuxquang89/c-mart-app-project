package com.example.cmart.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarDTO {

	private Long id;
	
	@NotNull(message = "The color is required.")
	@NotBlank(message="Please enter your color")
	private String color;
	
	@NotNull(message = "The car type is required.")
	@NotBlank(message="Please enter your car type")
	private String carType;
	
	@NotNull(message = "The car seating is required.")
	@NotBlank(message="Please enter your car seating")
	private int carSeating;
	
	@NotNull(message = "The car plate is required.")
	@NotBlank(message="Please enter your car plate")
	private String carPlate;
	
	@NotNull(message = "The car model is required.")
	@NotBlank(message="Please enter your car model")
	private String carModel;
	
	@NotNull(message = "The price is required.")
	@NotBlank(message="Please enter your car price")
	private float totalPrace;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
