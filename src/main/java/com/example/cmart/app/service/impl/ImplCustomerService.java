package com.example.cmart.app.service.impl;

import java.util.Optional;

import com.example.cmart.app.entity.CustomerEntity;

public interface ImplCustomerService {

	Optional<CustomerEntity> findById(long id);
	Optional<CustomerEntity> findCustomerByEmail(String email);
	Optional<CustomerEntity> findCustomerByUsername(String username);
	Optional<CustomerEntity> findCustomerByPhone(String phone);
	
	boolean existsCustomerQuery(String user);
	boolean existsCustomerQueryPhone(String phone);
	CustomerEntity save(CustomerEntity customer);
}
