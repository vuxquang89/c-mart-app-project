package com.example.cmart.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class CarEntity extends BaseEntity{

	@OneToMany(mappedBy = "car")
	private List<BookingEntity> bookings = new ArrayList<>();
	
	@OneToOne(mappedBy = "car")
	private DriverEntity driver;
	
	@Column(name = "car_type")
	String carType;
	
	@Column(name = "car_model")
	String carModel;
	
	@Column(name = "car_color")
	String carColor;
	
	//bien so
	@Column(name = "car_plate")
	String carPlate;
	
	@Column(name = "car_seating")
	Integer carSeating;
	
	@Column(name = "car_price")
	Double carPrice;

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public Integer getCarSeating() {
		return carSeating;
	}

	public void setCarSeating(Integer carSeating) {
		this.carSeating = carSeating;
	}

	public Double getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(Double carPrice) {
		this.carPrice = carPrice;
	}

	public List<BookingEntity> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookingEntity> bookings) {
		this.bookings = bookings;
	}

	public DriverEntity getDriver() {
		return driver;
	}

	public void setDriver(DriverEntity driver) {
		this.driver = driver;
	}
	
	
}
