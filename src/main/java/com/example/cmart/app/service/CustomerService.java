package com.example.cmart.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.repository.CustomerRepository;
import com.example.cmart.app.service.impl.ImplCustomerService;

@Service
public class CustomerService implements ImplCustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Optional<CustomerEntity> findCustomer(String email) {
		return customerRepository.findByEmail(email);
	}
}
