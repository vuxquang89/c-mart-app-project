package com.example.cmart.app.dto;

import java.time.LocalDateTime;

public class MessageDTO {

	private String message;
	private String fromLogin;
	private LocalDateTime createDate;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFromLogin() {
		return fromLogin;
	}
	public void setFromLogin(String fromLogin) {
		this.fromLogin = fromLogin;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
}
