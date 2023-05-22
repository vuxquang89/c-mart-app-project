package com.example.cmart.app.entity;

import java.util.ArrayList;
import java.util.Collection;
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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.cmart.app.util.DriverStatus;
import com.example.cmart.app.util.Gender;

@Entity
@Table(name = "drivers")
public class DriverEntity extends BaseEntity implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "car_id", referencedColumnName = "id")
	private CarEntity car;
	
	@OneToMany(mappedBy = "driver")
	private List<RatingEntity> ratings = new ArrayList<>();
	
	@Column
	private String fullname;
	
	@Column(unique = true)
	private String username;
	
	@Email
	@Column(unique = true)
	private String email;
	
	@Column
	private String password;
	
	@Column(unique = true,name = "phone_number")
    @Size(max = 15)
    private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
	
	//danh gia
	@Column
	private Float rating;
	
	@Column(name = "current_location_lat")
	private double currentLocationLat;
	
	@Column(name = "current_location_lng")
	private double currentLocationLng;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private DriverStatus status;
	
	@Column
	private String role;
	


	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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

	public DriverStatus getStatus() {
		return status;
	}

	public void setStatus(DriverStatus status) {
		this.status = status;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public String getUsername() {
		return username; 
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
