package com.example.cmart.app.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.ChatMessageConverter;
import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.entity.ChatMessageEntity;
import com.example.cmart.app.service.ChatMessageService;
import com.example.cmart.app.service.WSService;

@RestController
public class MessageControllerAPI {
	@Autowired
	private WSService wsService;
	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
	private ChatMessageConverter messageConvert;

	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, 
			MessageDTO message) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		System.out.println("user name : " + username); 
		
		System.out.println("Handling send message: " + message + " to " + to);
		
		ChatMessageEntity chatMessageEntity = messageConvert.toEntity(message);
		chatMessageEntity.setUsername(username);
		chatMessageService.save(chatMessageEntity);
		wsService.notifyFrontend(to, message);
	}
	
	@GetMapping("/messages")
	public ResponseEntity<?> getMessages() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		
		Pageable pageable = PageRequest.of(0, 10);
		List<MessageDTO> listMessages = chatMessageService.findAll(username, pageable);
		return new ResponseEntity<>(listMessages, HttpStatus.OK);
	}
}
