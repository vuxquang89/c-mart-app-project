package com.example.cmart.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
	public void testDate() {
		Date d = new Date();
		Date d2 = new Date("2023-05-13 15:20:44");
		System.out.println(d);
		assertThat("").isNotNull();
	}
	
	@Test
	public void testAddDriver() {
		/*
		CarEntity car = new CarEntity();
		car.setCarColor("blue");
		car.setCarModel("honda");
		car.setCarType("motobike");
		car.setCarSeating(2);
		car.setCarPrice(500f);
		car.setCarPlate("43H-23423");
		
		DriverEntity driver = new DriverEntity();
		driver.setCar(car);
		driver.setGender(Gender.male);
		driver.setEmail("vanminh@gmail.com");
		driver.setFullname("van minh");
		driver.setUsername("vanminh");
		driver.setPhoneNumber("0934845228");
		driver.setStatus(DriverStatus.waitting);
		driver.setCurrentLocationLat(16.05419159373633);//
		driver.setCurrentLocationLng(108.20908001404615);
		driver.setRating(3);
		
		*/
		CarEntity car2 = new CarEntity();
		car2.setCarColor("blue");
		car2.setCarModel("honda");
		car2.setCarType("motobike");
		car2.setCarSeating(2);
		car2.setCarPrice(500f);
		car2.setCarPlate("92H1-34423");
		
		DriverEntity driver2 = new DriverEntity();
		driver2.setCar(car2);
		driver2.setGender(Gender.male);
		driver2.setEmail("vanhung@gmail.com");
		driver2.setFullname("van hung");
		driver2.setUsername("vanhung");
		driver2.setPhoneNumber("0934234228");
		driver2.setStatus(DriverStatus.pick_up);
		driver2.setCurrentLocationLat(16.05419159373633);//
		driver2.setCurrentLocationLng(108.20908001404615);
		driver2.setRating(3);
		
		
		DriverEntity saveDriver = repo.save(driver2);
		assertThat(saveDriver).isNotNull();
		//repo.saveAll(List.of(driver, driver2));
		//long count = repo.count();
		//assertEquals(2, count);
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
