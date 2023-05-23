package com.example.cmart.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaymentMethodDTO {

	@NotNull(message = "The payment method is required.")
	@NotBlank(message = "The payment method is required.")
	private String paymentMethod;

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
