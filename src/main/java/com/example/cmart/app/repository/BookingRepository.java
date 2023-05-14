package com.example.cmart.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmart.app.entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>{

	
}