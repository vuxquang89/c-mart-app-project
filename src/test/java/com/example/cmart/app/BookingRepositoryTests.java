package com.example.cmart.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.cmart.app.converter.BookingConverter;
import com.example.cmart.app.converter.DateTimeConverter;
import com.example.cmart.app.dto.BookingDTO;
import com.example.cmart.app.dto.BookingRequestDTO;
import com.example.cmart.app.dto.CarDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.entity.CarEntity;
import com.example.cmart.app.entity.DriverEntity;
import com.example.cmart.app.repository.BookingRepository;
import com.example.cmart.app.service.BookingService;
import com.example.cmart.app.service.CarService;
import com.example.cmart.app.service.DriverService;
import com.example.cmart.app.util.BookingStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BookingRepositoryTests {

	
	@Autowired
	private BookingRepository repo;
	
	@Test
	public void addBooking() {
		BookingEntity request = new BookingEntity();
		request.setStartLocationLat(15.9866010);
		request.setStartLocationLng(108.2021670);
	    request.setStartAddress("");
	    request.setEndLocationLat(16.0255941); 
	    request.setEndLocationLng(108.1664135);
	    
	    request.setEndAddress("Trung tâm Hội nghị&Tiệc cưới Minh Châu Việt");
	    request.setStartTime("2023-05-18 08:43:32");
	    request.setDistance(9400);
	    //request.set(840);
	    
	    CarEntity car = new CarEntity();
	    car.setId(1l);
	    car.setCarSeating(2);
	    car.setCarType("motobike");
	    //car.setTotalPrace(34569);
	    
	    //request.setCar(car);
	    
	    
	    request.setCar(car);
		BookingEntity bookingSave = repo.save(request);
		
		assertThat(bookingSave).isNotNull();
		assertThat(bookingSave.getId()).isGreaterThan(0);
	}
}
