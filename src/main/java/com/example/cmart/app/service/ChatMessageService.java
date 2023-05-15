package com.example.cmart.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.cmart.app.converter.ChatMessageConverter;
import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.entity.ChatMessageEntity;
import com.example.cmart.app.repository.ChatMessageRepository;
import com.example.cmart.app.service.impl.ImplChatMessageService;

@Service
public class ChatMessageService implements ImplChatMessageService{

	@Autowired
	private ChatMessageRepository chatMessageRepo;
	
	@Autowired
	private ChatMessageConverter messageConvert;
	
	@Override
	public ChatMessageEntity save(ChatMessageEntity chatMessage) {
		return chatMessageRepo.save(chatMessage);
	}
	
	@Override
	public List<MessageDTO> findAll(String username, Pageable pageable) {
		List<MessageDTO> results = new ArrayList<MessageDTO>();
		List<ChatMessageEntity> messages = chatMessageRepo.findAllMessageUsername(username, pageable).getContent();
		
		for(ChatMessageEntity message : messages) {
			MessageDTO messDTO = messageConvert.toDTO(message);
			results.add(messDTO);
		}
		return results;
	}
}
