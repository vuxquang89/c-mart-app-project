package com.example.cmart.app.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.ContactSupportDTO;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.service.MailService;

@RestController
@RequestMapping("/api")
public class ContactSupportAPI {

	@Autowired
	private MailService mailService;
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JwtTokenService jwtService;
	
	@PostMapping("/customer/contact")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<?> submitContact(@RequestBody ContactSupportDTO contact,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException{
		
		String username = jwtService.getUserNameFromJwtSubject(jwtService.getToken(request));
		CustomerEntity customer = customerService.findCustomerByEmail(username).get();
		
		try {
			contact.setEmail(customer.getEmail());
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
