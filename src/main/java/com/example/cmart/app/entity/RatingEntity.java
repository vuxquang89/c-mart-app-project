package com.example.cmart.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
public class RatingEntity extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "driver_id")
    private DriverEntity driver;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
    private BookingEntity booking;
	
	@Column(name = "service_attitude")
	private Integer serviceAttitude; //thai do phuc vu
	
	@Column(name = "operating_speed")
	private Integer operatingSpeed; //toc do van hanh
	
	@Column
	private Integer safety; //tinh an toan
	
	@Column
	private Integer efficiency; //hieu qua
	
	@Column 
	private String comment;

	public Integer getServiceAttitude() {
		return serviceAttitude;
	}

	public void setServiceAttitude(Integer serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}

	public Integer getOperatingSpeed() {
		return operatingSpeed;
	}

	public void setOperatingSpeed(Integer operatingSpeed) {
		this.operatingSpeed = operatingSpeed;
	}

	public Integer getSafety() {
		return safety;
	}

	public void setSafety(Integer safety) {
		this.safety = safety;
	}

	public Integer getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(Integer efficiency) {
		this.efficiency = efficiency;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public DriverEntity getDriver() {
		return driver;
	}

	public void setDriver(DriverEntity driver) {
		this.driver = driver;
	}

	public BookingEntity getBooking() {
		return booking;
	}

	public void setBooking(BookingEntity booking) {
		this.booking = booking;
	}
	
	
}
