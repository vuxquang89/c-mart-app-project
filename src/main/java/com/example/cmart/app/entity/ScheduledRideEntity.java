package com.example.cmart.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "scheduledrides")
public class ScheduledRideEntity extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "customer_id")
    private CustomerEntity customer;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private CarEntity car;
	
	@Column(name = "start_location_lat")
	private double startLocationLat;
	
	@Column(name = "start_location_lng")
	private double startLocationLng;
	
	@Column(name = "start_address")
	private String startAddress;
	
	@Column(name = "end_location_lat")
	private double endLocationLat;
	
	@Column(name = "end_location_lng")
	private double endLocationLng;
	
	@Column(name = "end_address")
	private String endAddress;
	
	@Column(name = "start_time")
	private Date startTime; //thoi gian bat dau don 
	
	@Column(name = "total_fare")
	private float totalFare;
	
	@Column(name = "booking_time")
	private Date bookingTime;
	
	@Column
	private float distance;
	
	@Column(name = "ride_time")
	private int rideTime;//seconds

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public CarEntity getCar() {
		return car;
	}

	public void setCar(CarEntity car) {
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public float getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(float totalFare) {
		this.totalFare = totalFare;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
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
