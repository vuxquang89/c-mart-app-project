package com.example.cmart.app.dto;

public class PaymentInfoDTO {

	private long id;
	private float amount;
	private String contentPayment;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getContentPayment() {
		return contentPayment;
	}
	public void setContentPayment(String contentPayment) {
		this.contentPayment = contentPayment;
	}
	
	
}
