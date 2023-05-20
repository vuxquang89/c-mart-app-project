package com.example.cmart.app.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CustomerEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long>{

	List<BookingEntity> findByCustomer(CustomerEntity customer);
	
	@Query(value = "select * from bookings b where b.id = ?1 and b.status = 'finish'",
			nativeQuery = true)
	Optional<BookingEntity> findBookingFinishById(long id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from bookings b where b.id = ?1 and (b.status = 'finish' or b.status = 'cancel')",
			nativeQuery = true)
	void deleteBookingById(long id);
	
	@Query(value = "SELECT CASE WHEN EXISTS ( SELECT 1 FROM bookings b WHERE b.status = :status and b.id = :id) THEN 'true' ELSE 'false' END",
			nativeQuery = true)
	boolean existsBookingToStatus(@Param("status") String status, @Param("id") Long id);
}
