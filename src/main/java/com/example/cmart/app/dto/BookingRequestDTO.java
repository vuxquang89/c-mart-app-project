package com.example.cmart.app.dto;


public class BookingRequestDTO extends AbstractDTO<BookingRequestDTO>{

	private float lat;
	private float lng;
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	
	
}
