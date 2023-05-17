package com.example.cmart.app.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.cmart.app.entity.CustomerEntity;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final long EXPIRE_DURATION_ACCESS_TOKEN = 10 * 60 * 1000; //10m
	private static final long EXPIRE_DURATION_REFRESH_TOKEN = 30 * 60 * 1000; //30m
	
	@Value("${app.jwt.secret}") //lay gia tri tu file .properties
	private String secretKey;
	
	/*
	 * tao accessToken
	 */
	public String generateAccessToken(CustomerEntity user) {
		return Jwts.builder()
				.setSubject(user.getId()+ "," + user.getEmail())
				.setIssuer("Bearer")
				.claim("roles", "ROLE_USER")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION_ACCESS_TOKEN))//thoi gian het han
				.signWith(SignatureAlgorithm.HS512, secretKey)//tao chu ky
				.compact();
	}
	
	/*
	 * tao refresh token
	 */
	public String generateRefreshToken(CustomerEntity user) {
		return Jwts.builder()
				.setSubject(user.getId() +","+user.getEmail())
				.setIssuer("Bearer")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION_REFRESH_TOKEN))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
	
	/*
	 * xác minh JWT đã cho. Nó trả về true nếu JWT được xác minh hoặc false nếu ngược lại.
	 */
	public boolean validateToken(String token, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
		String exMessage = "";
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch(ExpiredJwtException ex) {
			exMessage = ex.getMessage();
			LOGGER.error("JWT expired", ex);
		}catch (IllegalArgumentException ex) {
			exMessage = ex.getMessage();
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
        	exMessage = ex.getMessage();
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
        	exMessage = ex.getMessage();
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
        	exMessage = ex.getMessage();
            LOGGER.error("Signature validation failed");
        }
		
		Map<String, String> error = new HashMap<String, String>();
		
		error.put("error_message", exMessage);
		new ObjectMapper().writeValue(response.getOutputStream(), error);
		
		return false;
	}
	
	public String getUserNameFromJwtSubject(String token) {
		
		Claims claims = parseClaims(token);
		String subject = (String)claims.get(Claims.SUBJECT);
		String[] jwtSubject = subject.split(",");
		return jwtSubject[1];
		
	}
	
	/*
	 *  lấy giá trị của trường chủ đề của mã thông báo đã cho. 
	 *  subject chứa ID người dùng và ten dang nhap, va được sử dụng để tạo lại đối tượng User
	 */
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
}
