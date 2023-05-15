package com.example.cmart.app.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.entity.ChatMessageEntity;

public interface ImplChatMessageService {

	ChatMessageEntity save(ChatMessageEntity chatMessage);
	List<MessageDTO> findAll(String username, Pageable pageable);
}
