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

import com.example.cmart.app.entity.BaseEntity;
import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.entity.RoleEntity;
import com.example.cmart.app.service.JwtTokenService;
import com.example.cmart.app.util.TypeUser;

import io.jsonwebtoken.Claims;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenService jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/customer/login") || 
				request.getServletPath().equals("/api/customer/token/refresh") ||
				request.getServletPath().equals("/api/driver/login") || 
				request.getServletPath().equals("/api/driver/token/refresh")) {
			filterChain.doFilter(request, response);
			return;
		}else {
			
		
			/*
			 * xac thuc Authorization Header chua ma bat dau voi "Bearer"
			 *
			 */
			if(!hasAuthorizationHeader(request)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String accessToken = jwtUtil.getToken(request);
			
						
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
		//CustomerEntity user = new CustomerEntity();
		//UserDetails user = null;
		Claims claims = jwtUtil.parseClaims(accessToken);
		String subject = (String)claims.get(Claims.SUBJECT);
		
		String[] jwtSubject = subject.split(",");
		if(jwtSubject[2].equals(TypeUser.CUSTOMER.name())) {
			System.out.println(jwtSubject[2]);
			CustomerEntity user = new CustomerEntity();
			
			user.setId(Long.parseLong(jwtSubject[0]));
			
			//user.setEmail(jwtSubject[1]);
			user.setUsername(jwtSubject[1]);
			
			String roles = (String)claims.get("roles");
			
			System.out.println("Claim Roles: "+roles);
			
			roles = roles.replace("[", "").replace("]", "");
			
			String[] roleNames = roles.split(",");
			
			for(String roleName : roleNames) {
				System.out.println("role : " + roleName);
				user.setRole(roleName.trim());
			}
			

			return user;
			
		}else if(jwtSubject[2].equals(TypeUser.DRIVER.name())){
			System.out.println(jwtSubject[2]);
			DriverEntity user = new DriverEntity();
			
			user.setId(Long.parseLong(jwtSubject[0]));
			
			//user.setEmail(jwtSubject[1]);
			user.setUsername(jwtSubject[1]);
			
			String roles = (String)claims.get("roles");
			
			System.out.println("Claim Roles: "+roles);
			
			roles = roles.replace("[", "").replace("]", "");
			
			String[] roleNames = roles.split(",");
			
			for(String roleName : roleNames) {
				System.out.println("role : " + roleName);
				user.setRole(roleName.trim());
			}
			
			return user;
		}
		
		return null;
		//String[] subjectArray = jwtUtil.getSubject(accessToken).split(",");
		//user.setId(Long.parseLong(subjectArray[0]));
		//user.setUsername(subjectArray[1]);
		
		
	}

	private boolean hasAuthorizationHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		System.out.println("Authorization header : " + header);
		
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}
	
}
