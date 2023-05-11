package com.example.cmart.app.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.jwt.JwtTokenFilter;
import com.example.cmart.app.oauth.CustomOAuth2UserService;
import com.example.cmart.app.service.CustomerService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = false,
		securedEnabled = false,
		jsr250Enabled = true //cho phep sử dụng chú thích @RolesAllowed trong mã API 
								//để duoc ủy quyền cấp phương thức
)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Autowired
	private CustomerService customerService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(
	            email -> customerService.findCustomer(email)
	                .orElseThrow(
	                    () -> new UsernameNotFoundException("Email " + email + " not found.")));
	}
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//show message
		http.exceptionHandling().authenticationEntryPoint(
				(request, response, ex) ->{
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					ex.getMessage());
				}
		);
		
		
		http.authorizeRequests()
			.antMatchers("/", "/api/customer/**", "/api/booking/**", "/api/login/**", "api/token/refresh/**","/oauth2/**").permitAll()
	        //.antMatchers("/login**", "/error**").permitAll()
	        .anyRequest().authenticated();
		
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	/*
	@Autowired
	private CustomOAuth2UserService oAuth2UserService;
	*/
}
