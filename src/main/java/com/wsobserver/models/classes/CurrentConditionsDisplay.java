package com.wsobserver.models.classes;

import com.wsobserver.models.interfaces.Display;
import com.wsobserver.models.interfaces.Observer;
import com.wsobserver.models.interfaces.Subject;

import java.util.UUID;

public class CurrentConditionsDisplay implements Observer, Display {
    WeatherData weatherData;
    private Subject weatherStation;
    private UUID name;
    public CurrentConditionsDisplay(Subject weatherStation){
        this.weatherStation = weatherStation;
        this.weatherStation.addObserver(this);
        name = UUID.randomUUID();
    }
    @Override
    public void display() {
        System.out.println("Current Condition : Temperature : " + weatherData.temperature + "Pressure : "+weatherData.temperature + "Humidity"+weatherData.humidity);
    }

    @Override
    public void update(WeatherData weatherData) {
        this.weatherData = weatherData;
        display();
    }
}
