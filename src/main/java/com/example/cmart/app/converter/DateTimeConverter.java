package com.example.cmart.app.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateTimeConverter {
	private DateTimeFormatter format;
	
	public DateTimeConverter() {
		format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}

	public long getSecondsOfDate(LocalDateTime t) {
		long h = t.getHour();
		long m = t.getMinute();
		long s = t.getSecond();
		return (h * 36000) + (m * 60) + s;
	}
	
	public String nowString() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(format);
	}
	
	public long getSecondsOfDate(String dateTime) {
		LocalDateTime localDateTime = parseLocalDateTime(dateTime);
		return getSecondsOfDate(localDateTime);
	}
	
	public LocalDateTime parseLocalDateTime(String dateString) {
		//DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(dateString, format);
	}
}
