package com.wsobserver.models.interfaces;

import com.wsobserver.models.classes.WeatherData;

import java.util.UUID;

public interface Observer {
    void update(WeatherData weatherData);
}