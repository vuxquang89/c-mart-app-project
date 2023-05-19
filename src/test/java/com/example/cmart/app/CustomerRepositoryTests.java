package com.example.cmart.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.cmart.app.entity.CustomerEntity;
import com.example.cmart.app.repository.CustomerRepository;
import com.example.cmart.app.util.AuthProvider;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
	
	@Autowired
	private CustomerRepository repo;

	@Test
	public void testAddCustomer() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "123456";
		String ancodedPassword = passwordEncoder.encode(rawPassword);
		
		CustomerEntity customer1 = new CustomerEntity();
		customer1.setEmail("quangvu@gmail.com");
		customer1.setFullname("quangvu");
		customer1.setPassword(ancodedPassword);
		customer1.setProvider(AuthProvider.local);
		customer1.setPhone("0769475843");
		
		CustomerEntity customerSave = repo.save(customer1);
		
		assertThat(customerSave).isNotNull();
		assertThat(customerSave.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testEditCustomer() {
		
		CustomerEntity customer1 = repo.findByEmail("vudqfx10478@funix.edu.vn").get();
		//customer1.setEmail("vudqfx10478@funix.edu.vn");
		customer1.setFullname("quangvu");
		
		customer1.setProvider(AuthProvider.local);
		customer1.setPhone("0769475843");
		
		CustomerEntity customerSave = repo.save(customer1);
		
		assertThat(customerSave).isNotNull();
		assertThat(customerSave.getId()).isGreaterThan(0);
	}
}
