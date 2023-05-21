package com.example.cmart.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cmart.app.entity.ScheduledRideEntity;

public interface ScheduledRideRepository extends JpaRepository<ScheduledRideEntity, Long>{

	List<ScheduledRideEntity> findByCarId(long id);
	
	List<ScheduledRideEntity> findByCustomerId(long id);
	
	@Query(value = "SELECT * FROM scheduledrides s "
			+ "WHERE s.id = ?1 and s.customer_id = ?2",
			nativeQuery = true)
	Optional<ScheduledRideEntity> findByCustomerId(Long id, Long customerId);
}
