package com.example.cmart.app.lib;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

	public static boolean checkPaasword(String pass, String hashPass) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
	    return bc.matches(pass, hashPass);
	}
	
	public static String encoderPassword(String pass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(pass);
	}
}
