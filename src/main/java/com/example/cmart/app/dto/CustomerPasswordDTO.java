package com.example.cmart.app.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class CustomerPasswordDTO {

	@NotEmpty
	@Length(min = 3, max = 200, message = "Password must be between 3 and 200 characters!")
	private String oldPassword;
	
	@NotEmpty
	@Length(min = 3, max = 200, message = "Password must be between 3 and 200 characters!")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
