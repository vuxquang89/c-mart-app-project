package com.example.cmart.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bookings")
public class BookingEntity extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "customer_id")
    private CustomerEntity customer;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private CarEntity car;
	
	@Column(name = "start_location_lat")
	private float startLocationLat;
	
	@Column(name = "start_location_lng")
	private float startLocationLng;
	
	@Column(name = "end_location_lat")
	private float endLocationLat;
	
	@Column(name = "end_location_lng")
	private float endLocationLng;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	@Column(name = "total_fare")
	private Double totalFare;
	
	@Column
	private String status;
	
	@Column(name = "booking_time")
	private Date bookingTime;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	@Column
	private float distance;
	
	@Column(name = "payment_method")
	private String paymentMethod;

	public float getStartLocationLat() {
		return startLocationLat;
	}

	public void setStartLocationLat(float startLocationLat) {
		this.startLocationLat = startLocationLat;
	}

	public float getStartLocationLng() {
		return startLocationLng;
	}

	public void setStartLocationLng(float startLocationLng) {
		this.startLocationLng = startLocationLng;
	}

	public float getEndLocationLat() {
		return endLocationLat;
	}

	public void setEndLocationLat(float endLocationLat) {
		this.endLocationLat = endLocationLat;
	}

	public float getEndLocationLng() {
		return endLocationLng;
	}

	public void setEndLocationLng(float endLocationLng) {
		this.endLocationLng = endLocationLng;
	}

	public Double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
