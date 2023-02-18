package com.wsobserver;

import com.wsobserver.models.classes.CurrentConditionsDisplay;
import com.wsobserver.models.classes.WeatherStation;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsObserverApplication {

	public static void main(String[] args) {
		WeatherStation weatherStation = new WeatherStation();
		CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherStation);
//		weatherStation.setMeasurement(2132,1343,43);
		weatherStation.setMeasurement();
		weatherStation.notifyObservers();
//		SpringApplication.run(WsObserverApplication.class, args);
	}
}
