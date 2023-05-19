package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.CustomerInfoDTO;
import com.example.cmart.app.dto.CustomerRegisterDTO;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.lib.Password;


@Component
public class CustomerConverter {

	public CustomerEntity toEntity(CustomerRegisterDTO dto) {
		CustomerEntity entity = new CustomerEntity();
		entity.setFullname(dto.getFullname());
		entity.setEmail(dto.getEmail());
		
		entity.setPassword(Password.encoderPassword(dto.getPassword()));
		entity.setUsername(dto.getUsername());
		entity.setPhone(dto.getPhone());
		return entity;
	}
	
	public CustomerInfoDTO infoToDTO(CustomerEntity entity) {
		CustomerInfoDTO dto = new CustomerInfoDTO();
		dto.setFullname(entity.getFullname());
		//dto.setEmail(entity.getEmail());
		dto.setPhone(entity.getPhone());
		//dto.setUsername(entity.getUsername());
		
		return dto;
	}
}
