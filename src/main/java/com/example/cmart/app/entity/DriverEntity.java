package com.example.cmart.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "drivers")
public class DriverEntity extends BaseEntity{

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "car_id", referencedColumnName = "id")
	private CarEntity car;
	
	@OneToMany(mappedBy = "driver")
	private List<RatingEntity> ratings = new ArrayList<>();
	
	@Column
	private String fullname;
	
	@Column
	private String username;
	
	@Email
	private String email;
	
	@Column
	private String password;
	
	//danh gia
	@Column
	private Integer rating;
	
	@Column(name = "current_location_lat")
	private float currentLocationLat;
	
	@Column(name = "current_location_lng")
	private float currentLocationLng;
	
	@Column(name = "status")
	private String status;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getCurrentLocationLat() {
		return currentLocationLat;
	}

	public void setCurrentLocationLat(float currentLocationLat) {
		this.currentLocationLat = currentLocationLat;
	}

	public float getCurrentLocationLng() {
		return currentLocationLng;
	}

	public void setCurrentLocationLng(float currentLocationLng) {
		this.currentLocationLng = currentLocationLng;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public CarEntity getCar() {
		return car;
	}

	public void setCar(CarEntity car) {
		this.car = car;
	}

	public List<RatingEntity> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingEntity> ratings) {
		this.ratings = ratings;
	}
	
}
