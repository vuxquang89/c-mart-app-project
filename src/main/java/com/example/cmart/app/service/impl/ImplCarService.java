package com.example.cmart.app.service.impl;

import java.util.Optional;

import com.example.cmart.app.entity.CarEntity;

public interface ImplCarService {

	Optional<CarEntity> findById(Long id);
	boolean existsByCarPlate(String plate);
}
