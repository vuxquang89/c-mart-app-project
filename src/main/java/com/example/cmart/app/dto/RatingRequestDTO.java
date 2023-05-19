package com.example.cmart.app.dto;

public class RatingRequestDTO {

	private int serviceAttitude; //thái độ phục vụ
	private int operatingSpeed; //tốc độ vận hành
	private int safe;//an toàn
	private int effective; //hiệu quả
	public int getServiceAttitude() {
		return serviceAttitude;
	}
	public void setServiceAttitude(int serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}
	public int getOperatingSpeed() {
		return operatingSpeed;
	}
	public void setOperatingSpeed(int operatingSpeed) {
		this.operatingSpeed = operatingSpeed;
	}
	public int getSafe() {
		return safe;
	}
	public void setSafe(int safe) {
		this.safe = safe;
	}
	public int getEffective() {
		return effective;
	}
	public void setEffective(int effective) {
		this.effective = effective;
	}
	
}
