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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.cmart.app.oauth.CustomOAuth2UserService;

@EnableWebSecurity
/*
@EnableGlobalMethodSecurity(
		prePostEnabled = false,
		securedEnabled = false,
		jsr250Enabled = true //cho phep sử dụng chú thích @RolesAllowed trong mã API 
								//để duoc ủy quyền cấp phương thức
)
*/
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}
	
	/*
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	*/
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
			.antMatchers("/oauth2/**").permitAll()
	        //.antMatchers("/login**", "/error**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	    .oauth2Login()	    
	    	//.userInfoEndpoint().userService(oAuth2UserService)
	        .defaultSuccessUrl("/success")
	        .and();
	    
	    //.oauth2Client();

	}
	
	@Autowired
	private CustomOAuth2UserService oAuth2UserService;
}
