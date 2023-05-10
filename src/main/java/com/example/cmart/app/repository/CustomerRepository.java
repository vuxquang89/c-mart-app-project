package com.example.cmart.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmart.app.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{

	Optional<CustomerEntity> findByEmail(String email);
}
