package com.example.cmart.app.dto;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

public class CustomerRequestDTO {

	@Email
	private String email;
	
	@Length(min = 5, max = 30)
	private String password;

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
	
	
}
