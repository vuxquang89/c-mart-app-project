package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.entity.ChatMessageEntity;

@Component
public class ChatMessageConverter {

	public ChatMessageEntity toEntity(MessageDTO dto) {
		ChatMessageEntity entity = new ChatMessageEntity();
		entity.setFromLogin(dto.getFromLogin());
		entity.setMessage(dto.getMessage());
		return entity;
	}
	
	public MessageDTO toDTO(ChatMessageEntity entity) {
		MessageDTO dto = new MessageDTO();
		dto.setFromLogin(entity.getFromLogin());
		dto.setMessage(entity.getMessage());
		dto.setCreateDate(entity.getCreateDate());
		return dto;
	}
}
