package com.example.cmart.app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.util.DriverStatus;

public interface ImplDriverService {

	List<DriverEntity> getDrivers(int rating, DriverStatus status);
	
	Optional<DriverEntity> findByCarId(Long carID);
	
	Optional<DriverEntity> findByPhoneNumber(String phone);
	boolean existsByPhone(String phone);
	boolean existsByEmail(String email);
	boolean existsByUsename(String username);
	
	DriverEntity save(DriverEntity driver);
}
