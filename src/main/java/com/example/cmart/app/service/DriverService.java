package com.example.cmart.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.repository.DriverRepository;
import com.example.cmart.app.service.impl.ImplDriverService;

@Service
public class DriverService implements ImplDriverService{

	@Autowired
	private DriverRepository driverRepository;
	
	@Override
	public List<DriverEntity> getDrivers(int rating, String status) {
		return driverRepository.getDrivers(rating, status);
	}
}
