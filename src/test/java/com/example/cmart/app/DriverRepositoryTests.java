package com.example.cmart.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.repository.DriverRepository;
import com.example.cmart.app.util.DriverStatus;
import com.example.cmart.app.util.Gender;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DriverRepositoryTests {

	@Autowired
	private DriverRepository repo;
	
	@Test
	public void testAddDriver() {
		CarEntity car = new CarEntity();
		car.setCarColor("blue");
		car.setCarModel("suzuky");
		car.setCarType("oto");
		car.setCarSeating(4);
		car.setCarPrice(1000f);
		car.setCarPlate("43H-23423");
		
		DriverEntity driver = new DriverEntity();
		driver.setCar(car);
		driver.setGender(Gender.female);
		driver.setEmail("honghoa@gmail.com");
		driver.setFullname("hoahong");
		driver.setUsername("hoahong");
		driver.setPhoneNumber("0903889228");
		driver.setStatus(DriverStatus.waitting);
		driver.setCurrentLocationLat(16.05419159373633);//
		driver.setCurrentLocationLng(108.20908001404615);
		driver.setRating(4);
		
		
		DriverEntity saveDriver = repo.save(driver);
		assertThat(saveDriver).isNotNull();
		
	}
	
	@Test
	public void testGetDriver() {
		int rating = 4;
		String status = "waitting";
		List<DriverEntity> drivers = repo.getDrivers(rating, status);
		System.out.println(Arrays.deepToString(drivers.toArray()));
		assertThat(drivers).isNotNull();
	}
	
	@Test
	public void testGetDriverWithCar() {
		int rating = 4;
		String status = "waitting";
		String carType = "oto";
		int carSeating = 4;
		List<String> genders = new ArrayList<String>();
		genders.add(Gender.male.name());
		genders.add(Gender.female.name());
		List<DriverEntity> drivers = repo.getDriversWithCar(rating, status, carType, carSeating, genders);
		System.out.println(drivers.size());
		System.out.println(Arrays.toString(drivers.toArray()));
		assertThat(drivers).isNotNull();
	}
}
