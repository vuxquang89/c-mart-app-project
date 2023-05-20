package com.example.cmart.app.dto;

public class RatingRequestDTO {

	private Long id;
	private int serviceAttitude; //thái độ phục vụ
	private int operatingSpeed; //tốc độ vận hành
	private int safe;//an toàn
	private int effective; //hiệu quả
	private String comment;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
