package com.example.cmart.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.cmart.app.entity.ScheduledRideEntity;

public interface ScheduledRideRepository extends JpaRepository<ScheduledRideEntity, Long>{

	List<ScheduledRideEntity> getByCarId(long id);
	
	List<ScheduledRideEntity> getByCustomerId(long id);
	
	@Query(value = "SELECT CASE WHEN EXISTS ( SELECT 1 FROM scheduledrides s WHERE s.id = :id and s.repeat_scheduledride = true) THEN 'true' ELSE 'false' END",
			nativeQuery = true)
	boolean checkRepeat(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM scheduledrides s "
			+ "WHERE s.id = ?1 and s.customer_id = ?2",
			nativeQuery = true)
	Optional<ScheduledRideEntity> findByCustomerId(Long id, Long customerId);
}
