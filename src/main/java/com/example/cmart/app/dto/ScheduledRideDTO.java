package com.example.cmart.app.dto;

import java.util.Date;

import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.CustomerEntity;

public class ScheduledRideDTO {

	private Long id;
	
	private CarDTO car;
	
	private double startLocationLat;
	
	private double startLocationLng;
	
	private String startAddress;
	
	private double endLocationLat;
	
	private double endLocationLng;
	
	private String endAddress;
	
	private String startTime; //thoi gian bat dau don 
	
	private float totalFare;
	
	private String bookingTime;
	
	private float distance;
	
	private int rideTime;//seconds

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CarDTO getCar() {
		return car;
	}

	public void setCar(CarDTO car) {
		this.car = car;
	}

	public double getStartLocationLat() {
		return startLocationLat;
	}

	public void setStartLocationLat(double startLocationLat) {
		this.startLocationLat = startLocationLat;
	}

	public double getStartLocationLng() {
		return startLocationLng;
	}

	public void setStartLocationLng(double startLocationLng) {
		this.startLocationLng = startLocationLng;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public double getEndLocationLat() {
		return endLocationLat;
	}

	public void setEndLocationLat(double endLocationLat) {
		this.endLocationLat = endLocationLat;
	}

	public double getEndLocationLng() {
		return endLocationLng;
	}

	public void setEndLocationLng(double endLocationLng) {
		this.endLocationLng = endLocationLng;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public float getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(float totalFare) {
		this.totalFare = totalFare;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getRideTime() {
		return rideTime;
	}

	public void setRideTime(int rideTime) {
		this.rideTime = rideTime;
	}
	
}
