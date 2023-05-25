package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.entity.ChatMessageEntity;

@Component
public class ChatMessageConverter {

	public ChatMessageEntity toEntity(MessageDTO dto) {
		ChatMessageEntity entity = new ChatMessageEntity();
		entity.setUsername(dto.getSenderName());
		entity.setFromLogin(dto.getReceiverName());
		entity.setMessage(dto.getMessage());
		entity.setCreateDate(dto.getDate());
		return entity;
	}
	
	public MessageDTO toDTO(ChatMessageEntity entity) {
		MessageDTO dto = new MessageDTO();
		dto.setSenderName(entity.getFromLogin());
		dto.setMessage(entity.getMessage());
		dto.setReceiverName(entity.getUsername());
		dto.setDate(entity.getCreateDate());
		return dto;
	}
}
