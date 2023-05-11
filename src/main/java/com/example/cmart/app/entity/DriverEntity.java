package com.example.cmart.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.example.cmart.app.util.Gender;

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
	
	@Column(name = "phone_number")
    @Size(max = 15)
    private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
	
	//danh gia
	@Column
	private Integer rating;
	
	@Column(name = "current_location_lat")
	private double currentLocationLat;
	
	@Column(name = "current_location_lng")
	private double currentLocationLng;
	
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

	public double getCurrentLocationLat() {
		return currentLocationLat;
	}

	public void setCurrentLocationLat(double currentLocationLat) {
		this.currentLocationLat = currentLocationLat;
	}

	public double getCurrentLocationLng() {
		return currentLocationLng;
	}

	public void setCurrentLocationLng(double currentLocationLng) {
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

	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
