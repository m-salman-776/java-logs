package DesignPatterns.wsobserver.models.classes;

import DesignPatterns.wsobserver.models.interfaces.Display;
import DesignPatterns.wsobserver.models.interfaces.Observer;
import DesignPatterns.wsobserver.models.interfaces.Subject;

public class ForecastDisplay implements Observer, Display {

    private Subject weatherStation;
    private WeatherData weatherData;
    public ForecastDisplay(Subject weatherStation){
        this.weatherStation = weatherStation;
        this.weatherStation.addObserver(this);
    }
    @Override
    public void display() {
        System.out.println("Forecast : Temperature : " + weatherData.temperature + "Pressure : "+weatherData.temperature + "Humidity"+weatherData.humidity);
    }

    @Override
    public void update(WeatherData weatherData) {
       this.weatherData = weatherData;
        display();
    }
}
