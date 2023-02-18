package com.wsobserver.models.classes;

import utility.RandomNumberGenerator;

public class WeatherData {
    public int id;
    public float humidity;
    public float temperature;
    public float pressure;
    public WeatherData(){

        this.humidity = RandomNumberGenerator.getRandomFloat();
        this.pressure = RandomNumberGenerator.getRandomFloat();
        this.temperature = RandomNumberGenerator.getRandomFloat();
    }
    public WeatherData(float temperature ,float humidity ,float pressure){

        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
    }
//    public WeatherData getWeatherData(){
//
//    }
    // TODO how to interact with db layer
    // todo how to add environment based configs
    // todo annotations
    // todo dao / dto farm service
}
