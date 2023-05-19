package com.example.cmart.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.cmart.app.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{

	Optional<CustomerEntity> findByEmail(String email);
	
	@Query(value = "SELECT * FROM customers c "
			+ "WHERE c.username = ?1 ",
			nativeQuery = true)
	Optional<CustomerEntity> findByUsername(String username);
	
	
	@Query(value = "SELECT CASE WHEN EXISTS ( SELECT 1 FROM customers c WHERE c.username = :user or c.email = :user) THEN 'true' ELSE 'false' END",
			nativeQuery = true)
	boolean existsCustomerQuery(@Param("user") String user);
	
}
