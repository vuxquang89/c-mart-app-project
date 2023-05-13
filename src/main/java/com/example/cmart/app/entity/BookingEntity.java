package com.example.cmart.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.cmart.app.util.BookingStatus;
import com.example.cmart.app.util.PaymentMethod;

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
	private String startTime; //thoi gian bat dau don 
	
	@Column(name = "end_time")
	private String endTime; //thoi gian den noi
	
	@Column(name = "total_fare")
	private float totalFare;
	
	@Column
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	
	@Column(name = "booking_time")
	private Date bookingTime;
	
	@Column(name = "payment_status")
	private boolean paymentStatus = false;
	
	@Column
	private float distance;
	
	@Column(name = "payment_method")
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

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

	public float getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(float totalFare) {
		this.totalFare = totalFare;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}

	public boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	
	
}
