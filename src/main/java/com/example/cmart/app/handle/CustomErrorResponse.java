package com.example.cmart.app.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomErrorResponse {

	private Date timestamp;
	private String status;
	private List<String> messages = new ArrayList<String>();
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
