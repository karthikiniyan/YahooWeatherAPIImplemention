package com.atelier.yahoo.WeatherReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class WeatherController {
	
	@Autowired
	private WeatherReport weather;
	
	@GetMapping("/get")
	public void getWeatherMetrics() {
		
		System.out.println("Inside getWeatherMetrics");
		
		try {
			weather.getWeatherReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
