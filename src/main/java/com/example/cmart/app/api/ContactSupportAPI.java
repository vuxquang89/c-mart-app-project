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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.ContactSupportDTO;

@RestController
@RequestMapping("/api")
public class ContactSupportAPI {

	@Autowired
    private JavaMailSender mailSender;
	
	@PostMapping("/customer/contact")
	public ResponseEntity<?> submitContact(@RequestBody ContactSupportDTO contact) throws UnsupportedEncodingException, MessagingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		String mailSubject = contact.getFullname() + " has sent a message";
		String mailContent = "<p><b>Sender Name :</b>" + contact.getFullname() + "</p>";
		mailContent += "<p><b>Sender E-mail:</b>"+contact.getEmail()+"</p>";
		mailContent += "<p><b>Subject:</b>"+contact.getSubject()+"</p>";
		mailContent += "<p><b>Content:</b>"+contact.getContent()+"</p>";
		
		helper.setFrom("vux.quang89@gmail.com", "C-Mart");
		helper.setTo("vudqfx10478@funix.edu.vn");
		helper.setSubject(mailSubject);
		helper.setText(mailContent, true);
		
		mailSender.send(message);
		Map<String, String> mess = new HashMap<String, String>();
		mess.put("result", "Send mail success!");
		return ResponseEntity.ok(mess);
	}
}
