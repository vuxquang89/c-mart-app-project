package com.example.cmart.app.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.cmart.app.jwt.JwtTokenFilter;
import com.example.cmart.app.service.CustomerService;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.util.TypeUser;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = false,
		securedEnabled = false,
		jsr250Enabled = true //cho phep sử dụng chú thích @RolesAllowed trong mã API 
								//để duoc ủy quyền cấp phương thức
)

public class SecurityConfig {

	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DriverService driverService;
	/*
	@Bean
	public BookingService bookService() {
		return new BookingService();
	}
	*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
				
			@Override
			public UserDetails loadUserByUsername(String userlogin) throws UsernameNotFoundException {
				System.out.println("userdetails service load");
				String[] userInfo = userlogin.split(","); 
				if(userInfo[1].equals(TypeUser.CUSTOMER.name())) {
					return customerService.findCustomerByEmail(userInfo[0])
							.orElseThrow(()->new UsernameNotFoundException("User " + userInfo[0] + " not found"));
				}else {
					return driverService.findByPhoneNumber(userInfo[0])
							.orElseThrow(()-> new UsernameNotFoundException("Phone " + userInfo[0] + " not found"));
				}
			}
		};
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.cors();
		http.csrf().disable();
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//show message
		http.exceptionHandling().authenticationEntryPoint(
						(request, response, ex) ->{
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							ex.getMessage());
						}
		);
		
		http.authorizeRequests()
				.antMatchers("/", "/chat/**"
						).permitAll()
				.antMatchers("/api/customer/login/**", 
						"/api/customer/token/refresh/**", 
						"/api/customer/register/**",
						"/api/customer/vnpay-payment/**"
						).permitAll()
				.antMatchers("/api/driver/login/**", 
						"/api/driver/token/refresh/**", 
						"/api/driver/register/**"						
						).permitAll()
				.antMatchers("/api/customer/**").hasAuthority("ROLE_USER")
				.antMatchers("/api/driver/**").hasAuthority("ROLE_DRIVER")
				.anyRequest().authenticated()
				.and()
	            .formLogin().permitAll()
				.and().oauth2Login(
						oc -> oc						
							//.loginProcessingUrl("/login")
							//.loginPage("/login")
							.defaultSuccessUrl("/api/google/success")
						)
				
					
				;
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
