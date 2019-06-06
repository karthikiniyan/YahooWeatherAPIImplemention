package com.atelier.yahoo.WeatherReport;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WeatherReportApplication {

	public static void main(String[] args) throws BeansException, Exception {
		
		
		ConfigurableApplicationContext cont =  SpringApplication.run(WeatherReportApplication.class, args);
		
		//cont.getBean(WeatherReport.class).getWeatherReport();
	}

}
