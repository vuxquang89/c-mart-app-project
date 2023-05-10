package com.example.cmart.app.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.cmart.app.entity.CustomerEntity;

import io.jsonwebtoken.Claims;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
			filterChain.doFilter(request, response);
			return;
		}else {
			
		
			/*
			 * xac thuc Authorization Header chua ma bat dau voi "Example"
			 *
			 */
			if(!hasAuthorizationHeader(request)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String accessToken = getAccessToken(request);
			
						
			if(!jwtUtil.validateToken(accessToken, response)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			setAuthorizationContext(accessToken, request);	
			filterChain.doFilter(request, response);
		}
	}
	
	private void setAuthorizationContext(String accessToken, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(accessToken);
		
		UsernamePasswordAuthenticationToken authentication 
			= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private UserDetails getUserDetails(String accessToken) {
		CustomerEntity user = new CustomerEntity();
		Claims claims = jwtUtil.parseClaims(accessToken);
		String subject = (String)claims.get(Claims.SUBJECT);
		String roles = (String)claims.get("roles");
		
		System.out.println("Claim Roles: "+roles);
		
		roles = roles.replace("[", "").replace("]", "");
		
		//String[] roleNames = roles.split(",");
		/*
		for(String roleName : roleNames) {
			user.addRole(new RoleEntity(roleName.trim()));
		}
		*/
		//String[] subjectArray = jwtUtil.getSubject(accessToken).split(",");
		//user.setId(Long.parseLong(subjectArray[0]));
		//user.setUsername(subjectArray[1]);
		String[] jwtSubject = subject.split(",");
		user.setId(Long.parseLong(jwtSubject[0]));
		
		user.setEmail(jwtSubject[1]);
		
		return user;
	}

	private boolean hasAuthorizationHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		System.out.println("Authorization header : " + header);
		
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Example")) {
			return false;
		}
		return true;
	}
	
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		
		System.out.println("Access Token : " + token);
		
		return token;
	}
}
