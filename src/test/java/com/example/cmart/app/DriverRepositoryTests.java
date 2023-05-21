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
		
		CarEntity car = new CarEntity();
		car.setCarColor("blue");
		car.setCarModel("honda civic");
		car.setCarType("car");
		car.setCarSeating(4);
		car.setCarPrice(800f);
		car.setCarPlate("43H-23431");
		
		DriverEntity driver = new DriverEntity();
		driver.setCar(car);
		driver.setGender(Gender.male);
		driver.setEmail("hongduc@gmail.com");
		driver.setFullname("hong duc");
		driver.setUsername("hongduc");
		driver.setPhoneNumber("0934834564");
		driver.setStatus(DriverStatus.waitting);
		driver.setCurrentLocationLat(16.044750);//, 108.209843
		driver.setCurrentLocationLng(108.209843);
		driver.setRating(4f);
		
		
		CarEntity car2 = new CarEntity();
		car2.setCarColor("white");
		car2.setCarModel("honda");
		car2.setCarType("motobike");
		car2.setCarSeating(2);
		car2.setCarPrice(500f);
		car2.setCarPlate("47H1-45632");
		
		DriverEntity driver2 = new DriverEntity();
		driver2.setCar(car2);
		driver2.setGender(Gender.female);
		driver2.setEmail("thihong@gmail.com");
		driver2.setFullname("huynh thi hong");
		driver2.setUsername("thihong");
		driver2.setPhoneNumber("0903882994");
		driver2.setStatus(DriverStatus.waitting);
		driver2.setCurrentLocationLat(16.047874);//, 
		driver2.setCurrentLocationLng(108.209423);
		driver2.setRating(3f);
		
		
		//DriverEntity saveDriver = repo.save(driver2);
		//assertThat(saveDriver).isNotNull();
		repo.saveAll(List.of(driver, driver2));
		long count = repo.count();
		assertEquals(2, count);
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
		int rating = 2;
		String status = "waitting";
		String carType = "motobike";
		int carSeating = 2;
		List<String> genders = new ArrayList<String>();
		genders.add(Gender.male.name());
		genders.add(Gender.female.name());
		List<DriverEntity> drivers = repo.getDriversWithCar(rating, status, carType, carSeating, genders);
		System.out.println(drivers.size());
		System.out.println(Arrays.toString(drivers.toArray()));
		assertThat(drivers).isNotNull();
	}
	
	@Test
	public void getDriver() {
		DriverEntity driverEntity = repo.findByCarId(1l).orElse(null);
		System.out.println(driverEntity.getId());
		assertThat(driverEntity).isNotNull();
	}
}
