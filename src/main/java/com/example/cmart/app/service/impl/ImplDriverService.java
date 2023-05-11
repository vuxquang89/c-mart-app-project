package com.example.cmart.app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.cmart.app.entity.DriverEntity;

public interface ImplDriverService {

	List<DriverEntity> getDrivers(int rating, String status);
	
	Optional<DriverEntity> findByCarId(Long carID);
}
