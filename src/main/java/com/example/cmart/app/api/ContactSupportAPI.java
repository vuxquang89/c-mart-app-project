package com.example.cmart.app.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.ContactSupportDTO;
import com.example.cmart.app.service.MailService;

@RestController
@RequestMapping("/api")
public class ContactSupportAPI {

	@Autowired
	private MailService mailService;
	
	@PostMapping("/customer/contact")
	public ResponseEntity<?> submitContact(@RequestBody ContactSupportDTO contact) throws UnsupportedEncodingException, MessagingException{
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		System.out.println("contact support - user name : " + username);
		
		try {
			boolean sendMail = mailService.sendEmail(contact);
			Map<String, String> mess = new HashMap<String, String>();
			String message = "";
			if(sendMail) {
				message = "Send mail success!";
			}else {
				message = "Send mail failed!";				
			}
			
			mess.put("result", message);
			return ResponseEntity.ok(mess);
			
		}catch (Exception e) {
			System.out.println(e.toString());
			return ResponseEntity.badRequest().build();
		}
	}
}
