package com.wsobserver.models.classes;

import com.wsobserver.models.interfaces.Display;
import com.wsobserver.models.interfaces.Observer;
import com.wsobserver.models.interfaces.Subject;

public class StatisticsDisplay implements Observer, Display {
    private WeatherData weatherData;
    private Subject weatherStation;
    public StatisticsDisplay(Subject weatherStation){
        this.weatherStation = weatherStation;
        this.weatherStation.addObserver(this);
    }
    @Override
    public void display() {
        System.out.println("Stats : Temperature : " + weatherData.temperature + "Pressure : "+weatherData.temperature + "Humidity"+weatherData.humidity);

    }
    @Override
    public void update(WeatherData weatherData) {
        this.weatherData = weatherData;
        display();
    }
}
