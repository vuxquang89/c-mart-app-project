package com.example.cmart.app.converter;

import org.springframework.stereotype.Component;

@Component
public class DistanceConverter {

	/*
	public double getDistance(float startLat, float startLng, float endLat, float endLng) {
		float R = (float) 3958.8; //radius of the Earth in miles 
		float rLat1 = (float) (startLat*(Math.PI/180)); //convert degrees to radians
		float rLat2 = (float)(endLat *(Math.PI/180)); //
		
		float diffLat = rLat2 - rLat1; //radian difference(latitudes)
		float diffLng = (float) ((endLng - startLng) * (Math.PI/180)); //radian difference(longitudes)
		
		double d = 2 * R * Math.asin(Math.sqrt(Math.sin(diffLat/2) * Math.sin(diffLat /2) + Math.cos(rLat1) * Math.cos(rLat2) * Math.sin(diffLng/2)*Math.sin(diffLng/2)));
		System.out.println("distance = " + d);
		return d;
	}
	*/
	
	/**
	 * Converts degrees to radians.
	 * 
	 * @param degrees Number of degrees.
	 */
	private double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180;
	}
	
	/**
	 * Returns the distance between 2 points of coordinates in Google Maps
	 * 
	 * @see https://stackoverflow.com/a/1502821/4241030
	 * @param startLat Latitude of the point start
	 * @param startLng Longitude of the point start
	 * @param endLat Latitude of the point end
	 * @param endLng Longitude of the point end
	 */
	public double getDistance(double startLat, double startLng, double endLat, double endLng) {
		float R = 6378137;// Earth’s mean radius in meter
		double dLat = degreesToRadians(endLat - startLat);
		double dLng = degreesToRadians(endLng - startLng);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
			    Math.cos(degreesToRadians(startLat)) * Math.cos(degreesToRadians(startLat)) *
			    Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c; // returns the distance in meter
		
	}
}
