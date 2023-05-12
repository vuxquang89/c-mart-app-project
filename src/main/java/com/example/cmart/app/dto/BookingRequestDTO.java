package com.example.cmart.app.dto;

import java.util.Date;

/**
 * nhận request từ người dùng
 * @author vux
 * vị trí người dùng đang đứng 
 * vị trí người dùng muốn đến
 * khoảng cách di chuyển giữa hai điểm
 */

public class BookingRequestDTO extends AbstractDTO<BookingRequestDTO>{

	private double startLat;
	private double startLng;
	private String startAddress;
	private double endLat;
	private double endLng;
	private String endAddress;
	private float distanceTransfer;
	private Date startTime;
	private int rideTime;//seconds
	
	private CarDTO car;
	
	public double getStartLat() {
		return startLat;
	}
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}
	public double getStartLng() {
		return startLng;
	}
	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}
	
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public double getEndLng() {
		return endLng;
	}
	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}
	
	public float getDistanceTransfer() {
		return distanceTransfer;
	}
	public void setDistanceTransfer(float distanceTransfer) {
		this.distanceTransfer = distanceTransfer;
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
	public CarDTO getCar() {
		return car;
	}
	public void setCar(CarDTO car) {
		this.car = car;
	}
	public int getRideTime() {
		return rideTime;
	}
	public void setRideTime(int rideTime) {
		this.rideTime = rideTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartDate(Date startTime) {
		this.startTime = startTime;
	}
	
	
}
