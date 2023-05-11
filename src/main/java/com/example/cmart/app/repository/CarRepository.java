package com.example.cmart.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmart.app.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long>{

}
