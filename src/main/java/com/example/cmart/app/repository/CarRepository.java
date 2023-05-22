package com.example.cmart.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.cmart.app.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long>{

	@Query(value = "SELECT CASE WHEN EXISTS ( SELECT 1 FROM cars c WHERE c.car_plate = :carPlate) THEN 'true' ELSE 'false' END",
			nativeQuery = true)
	boolean existsByCarPlate(@Param("carPlate") String carPlate);
}
