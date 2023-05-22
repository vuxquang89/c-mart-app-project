package com.example.cmart.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class DriverRegisterDTO {

	private String fullname;
	@NotNull(message = "The username is required.")
	@NotBlank(message="Please enter your username")
	@Length(min = 8)
	private String username;
	
	@NotNull(message = "The phone is required.")
	@NotBlank(message="Please enter your phone number")
	@Pattern(regexp = "^\\d{10}$")
	private String phone;
	
	@NotBlank(message="Please enter your password")
	@NotNull(message = "The password is required.")
	@Length(min = 8)
	private String password;
	private int age;
	private String gender;
	@Email
	@NotEmpty(message = "The email address is required.")
	private String email;
	private CarDTO car;
	
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
	public String getPhone() {
		return phone;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CarDTO getCar() {
		return car;
	}
	public void setCar(CarDTO car) {
		this.car = car;
	}
	
}
