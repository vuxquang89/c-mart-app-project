package com.example.cmart.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CustomerEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long>{

	List<BookingEntity> findByCustomer(CustomerEntity customer);
}
