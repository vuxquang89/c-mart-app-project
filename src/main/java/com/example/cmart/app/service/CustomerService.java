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
	public Optional<CustomerEntity> findCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	@Override
	public Optional<CustomerEntity> findCustomerByUsername(String username) {
		return customerRepository.findByUsername(username);
	}
	
	@Override
	public CustomerEntity save(CustomerEntity customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public Optional<CustomerEntity> findById(long id) {
		return customerRepository.findById(id);
	}
	
	@Override
	public boolean existsCustomerQuery(String user) {
		return customerRepository.existsCustomerQuery(user);
	}
	
	@Override
	public Optional<CustomerEntity> findCustomerByPhone(String phone) {
		return customerRepository.findByPhone(phone);
	}
	@Override
	public boolean existsCustomerQueryPhone(String phone) {
		return customerRepository.existsCustomerQueryPhone(phone);
	}
}
