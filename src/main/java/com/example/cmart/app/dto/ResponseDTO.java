package com.example.cmart.app.dto;

import java.util.List;

import com.example.cmart.app.entity.DriverEntity;

public class ResponseDTO {

	private List<DriverEntity> listDriver;

	public List<DriverEntity> getListDriver() {
		return listDriver;
	}

	public void setListDriver(List<DriverEntity> listDriver) {
		this.listDriver = listDriver;
	}
	
	
}
