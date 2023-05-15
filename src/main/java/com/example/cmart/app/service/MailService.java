package com.example.cmart.app.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.cmart.app.dto.ContactSupportDTO;

@Service
public class MailService {

	@Autowired
    private JavaMailSender mailSender;
	
	public boolean sendEmail(ContactSupportDTO contact) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		String mailSubject = contact.getFullname() + " has sent a message";
		String mailContent = "<p><b>Sender Name :</b>" + contact.getFullname() + "</p>";
		mailContent += "<p><b>Sender E-mail:</b>"+contact.getEmail()+"</p>";
		mailContent += "<p><b>Subject:</b>"+contact.getSubject()+"</p>";
		mailContent += "<p><b>Content:</b>"+contact.getContent()+"</p>";
		
		try {
			helper.setFrom("application.cmart@gmail.com", "C-Mart");
			helper.setTo("vudqfx10478@funix.edu.vn");
			helper.setSubject(mailSubject);
			helper.setText(mailContent, true);
			mailSender.send(message);
			return true;
		} catch (UnsupportedEncodingException | MessagingException e) {
		
			System.out.println(e.toString());
			return false;
		}
		
		
		
	}
}
