package DesignPatterns.wsobserver.models.interfaces;

import DesignPatterns.wsobserver.models.classes.WeatherData;

public interface Observer {
    void update(WeatherData weatherData);
}