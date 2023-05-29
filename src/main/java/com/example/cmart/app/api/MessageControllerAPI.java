package com.example.cmart.app.api;



import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.converter.ChatMessageConverter;
import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.dto.MessageDTO;
import com.example.cmart.app.entity.ChatMessageEntity;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.service.ChatMessageService;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.service.WSService;

@RestController
//@CrossOrigin
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageControllerAPI {
	@Autowired
	private WSService wsService;
	
	@Autowired
	private JwtTokenService jwtService;
	
	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ChatMessageConverter messageConvert;
	
	@Autowired
	private DateTimeConverter dateTimeConvert;

	@MessageMapping("/message")
    @SendTo("/chatroom/public")
    public MessageDTO receiveMessage(@Payload MessageDTO message){
		
        return message;
    }

    @MessageMapping("/private-message")
    public MessageDTO recMessage(@Payload MessageDTO message){
    	message.setDate(dateTimeConvert.nowString());
        wsService.sendMessage(message);
        ChatMessageEntity chatMessageEntity = messageConvert.toEntity(message);
		//chatMessageEntity.setUsername(message.getSenderName());
		chatMessageEntity.setCreateDate(dateTimeConvert.parseLocalDateTime(dateTimeConvert.nowString()));
		chatMessageEntity.setRoomUser("C-Mart - " + message.getSenderName());
		chatMessageService.save(chatMessageEntity);
		//wsService.notifyFrontend(to, message);
        return message;
    }
    
    @GetMapping("/messages/customer")
	public ResponseEntity<?> getMessages(
			HttpServletRequest request) {
		
    	String username = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(username).get();
		
		Pageable pageable = PageRequest.of(0, 20);
		List<MessageDTO> listMessages = chatMessageService.findAll(customer.getUsername(), pageable);
		Map<String, Object> mess = new HashMap<String, Object>();
		mess.put("body", listMessages);
		return new ResponseEntity<>(mess, HttpStatus.OK);
	}
    
	/*
	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, 
			MessageDTO message) {
		
	
		System.out.println("Handling send message: " + message + " to " + to);
		
		ChatMessageEntity chatMessageEntity = messageConvert.toEntity(message);
		chatMessageEntity.setUsername(message.getFromLogin());
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
	*/
	
	
}
