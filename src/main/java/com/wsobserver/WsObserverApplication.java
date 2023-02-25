package com.wsobserver;

import com.wsobserver.models.classes.CurrentConditionsDisplay;
import com.wsobserver.models.classes.WeatherStation;
import com.wsobserver.repository.dbImpl.DbMock;
import com.wsobserver.service.WsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsObserverApplication {

	public static void main(String[] args) {
		WsService wsService = new WsService();
//		CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherStation);
////		weatherStation.setMeasurement(2132,1343,43);
//		weatherStation.setMeasurement();
//		weatherStation.notifyObservers();
		SpringApplication.run(WsObserverApplication.class, args);
	}
}
