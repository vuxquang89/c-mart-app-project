package com.example.cmart.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.cmart.app.service.JwtTokenService;

public class AppConstants {

	public static final int RATING_ONE_STAR = 1;
	public static final int RATING_TOW_STAR = 2;
	public static final int RATING_THREE_STAR = 3;
	public static final int RATING_FOUR_STAR = 4;
	public static final int RATING_FIVE_STAR = 5;
	public static final int HOUR_TO_SECONDS = 3600;
	public static final int DEFAULT_RADIUS_LOCATION = 500;
	public static final int RADIUS_LOCATION_5000_M = 5000;
	public static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);
	public static final long EXPIRE_DURATION_ACCESS_TOKEN = 30 * 60 * 1000; //10m
	public static final long EXPIRE_DURATION_REFRESH_TOKEN = 3 * 60 * 60 * 1000; //
}
