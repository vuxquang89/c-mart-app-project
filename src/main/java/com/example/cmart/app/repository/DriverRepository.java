package com.example.cmart.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cmart.app.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, Long>{

	@Query(value = "SELECT * FROM drivers d "
			+ "WHERE d.rating >= :rating and d.status = :status",
			nativeQuery = true)
	List<DriverEntity> getDrivers(int rating, String status); 
}
