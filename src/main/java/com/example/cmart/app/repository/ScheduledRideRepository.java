package com.example.cmart.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmart.app.entity.ScheduledRideEntity;

public interface ScheduledRideRepository extends JpaRepository<ScheduledRideEntity, Long>{

	List<ScheduledRideEntity> findByCarId(long id);
	
	List<ScheduledRideEntity> findByCustomerId(long id);
}
