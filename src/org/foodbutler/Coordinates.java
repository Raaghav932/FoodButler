package org.foodbutler;

public class Coordinates {
	private double lat;
	private double lon;
	
	public Coordinates(double lat, double lon)
	{
		this.lat = lat;
		this.lon = lon;
	}
	
	public Coordinates(String lat, String lon)
	{
		this(Double.parseDouble(lat), Double.parseDouble(lon));
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "Coordinates [lat=" + lat + ", lon=" + lon + "]";
	}
}
