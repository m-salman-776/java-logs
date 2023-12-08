package DesignPatterns.wsobserver;

import DesignPatterns.wsobserver.service.WsService;
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
