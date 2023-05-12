package com.example.cmart.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.repository.DriverRepository;
import com.example.cmart.app.service.impl.ImplDriverService;
import com.example.cmart.app.util.DriverStatus;

@Service
public class DriverService implements ImplDriverService{

	@Autowired
	private DriverRepository driverRepository;
	
	@Override
	public List<DriverEntity> getDrivers(int rating, DriverStatus status) {
		return driverRepository.getDrivers(rating, status.name());
	}
	
	@Override
	public Optional<DriverEntity> findByCarId(Long carID) {
		return driverRepository.findByCarId(carID);
	}
	
	@Override
	public DriverEntity save(DriverEntity driver) {
		return driverRepository.save(driver);
	}
}
