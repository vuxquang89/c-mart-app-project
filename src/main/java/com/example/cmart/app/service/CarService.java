package com.example.cmart.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.repository.CarRepository;
import com.example.cmart.app.service.impl.ImplCarService;

@Service
public class CarService implements ImplCarService{

	@Autowired
	private CarRepository carRepo;
	
	@Override
	public Optional<CarEntity> findById(Long id) {
		return carRepo.findById(id);
	}
}
