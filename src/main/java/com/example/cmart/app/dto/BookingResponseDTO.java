package com.example.cmart.app.dto;

import java.util.List;

public class BookingResponseDTO {

	private BookingDTO booking;
	private DriverDTO driver;

	public BookingResponseDTO() {}
	
	public BookingResponseDTO(BookingDTO booking, DriverDTO driver) {
		this.booking = booking;
		this.driver = driver;
	}
	
	public DriverDTO getDriver() {
		return driver;
	}

	public void setDriver(DriverDTO driver) {
		this.driver = driver;
	}

	public BookingDTO getBooking() {
		return booking;
	}

	public void setBooking(BookingDTO booking) {
		this.booking = booking;
	}
	
	
}
