package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

@Component
public class DistanceConverter {

	public double getDistance(float startLat, float startLng, float endLat, float endLng) {
		float R = (float) 3958.8; //radius of the Earth in miles 
		float rLat1 = (float) (startLat*(Math.PI/180)); //convert degrees to radians
		float rLat2 = (float)(endLat *(Math.PI/180)); //
		
		float diffLat = rLat2 - rLat1; //radian difference(latitudes)
		float diffLng = (float) ((endLng - startLng) * (Math.PI/180)); //radian difference(longitudes)
		
		double d = 2 * R * Math.asin(Math.sqrt(Math.sin(diffLat/2) * Math.sin(diffLat /2) + Math.cos(rLat1) * Math.cos(rLat2) * Math.sin(diffLng/2)*Math.sin(diffLng/2)));
		return d;
	}
}
