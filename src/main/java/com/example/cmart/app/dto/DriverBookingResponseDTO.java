package com.example.cmart.app.dto;

public class DriverBookingResponseDTO {

	private Long id;
	private String customerName;
	private String customerPhone;
	private double startLocationLat;
	private double startLocationLng;
	private double endLocationLat;
	private double endLocationLng;
	private String status;
	private float totalFrice;
	private String startTime;
	private String endTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
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
	public float getTotalFrice() {
		return totalFrice;
	}
	public void setTotalFrice(float totalFrice) {
		this.totalFrice = totalFrice;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
