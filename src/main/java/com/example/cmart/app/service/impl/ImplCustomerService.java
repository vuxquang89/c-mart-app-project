package com.example.cmart.app.service.impl;

import java.util.Optional;

import com.example.cmart.app.entity.CustomerEntity;

public interface ImplCustomerService {

	Optional<CustomerEntity> findById(long id);
	Optional<CustomerEntity> findCustomer(String email);
	Optional<CustomerEntity> findCustomerByUsername(String username);
	boolean existsCustomerQuery(String user);
	CustomerEntity save(CustomerEntity customer);
}
