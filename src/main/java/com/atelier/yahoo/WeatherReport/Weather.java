package com.atelier.yahoo.WeatherReport;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

@Component
public class Weather implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6872310476505347035L;
	private String country;
	private String state;
	private String city;
	private Double temp;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Double getTemp() {
		return temp;
	}
	
	@SuppressWarnings("deprecation")
	@Required
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	@Override
	public String toString() {
		return "Weather [country=" + country + ", state=" + state + ", city=" + city + ", temp=" + temp + "]";
	}
	
	

}
