package com.example.cmart.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cmart.app.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, Long>{

	@Query(value = "SELECT * FROM drivers d "
			+ "WHERE d.rating >= :rating and d.status = :status",
			nativeQuery = true)
	List<DriverEntity> getDrivers(int rating, String status);
	
	Optional<DriverEntity> findByCarId(Long carID);
	
	@Query(value = "select * from drivers d join cars c on d.car_id = c.id "
			+ "where d.rating >= :rating and d.status = :status and d.gender in (:genders) "
			+ "and c.car_type = :carType and c.car_seating = :carSeating ORDER BY d.rating DESC",
			nativeQuery = true)
	List<DriverEntity> getDriversWithCar(int rating, String status, String carType, int carSeating, List<String> genders);
}
