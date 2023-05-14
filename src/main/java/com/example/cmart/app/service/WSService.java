package com.example.cmart.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.cmart.app.dto.MessageDTO;

@Service
public class WSService {

	private final SimpMessagingTemplate messageTemplate;
	
	@Autowired
	public WSService(SimpMessagingTemplate messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	
	public void notifyFrontend(String to, MessageDTO message) {
		messageTemplate.convertAndSend("/topic/messages/" + to, message);
	}
}
