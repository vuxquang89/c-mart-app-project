package com.example.cmart.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.service.WSService;

@RestController
public class MessageControllerAPI {
	@Autowired
	private WSService wsService;

	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, MessageDTO message) {
		System.out.println("Handling send message: " + message + " to " + to);
		wsService.notifyFrontend(to, message);
	}
}
