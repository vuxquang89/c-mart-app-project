package com.example.cmart.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerInfoDTO {

	
	private String fullname;
	
	//@NotEmpty
	//@Size(min = 2, message = "username should have at least 2 characters")
	//private String username;
	
	@Pattern(regexp="(^$|[0-9]{10})", message = "Phone number format is incorrect")
	private String phone;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/*
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	*/
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
