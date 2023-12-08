package DesignPatterns.wsobserver.models.classes;

import java.util.ArrayList;
import DesignPatterns.wsobserver.models.interfaces.Observer;
import DesignPatterns.wsobserver.models.interfaces.Subject;


public class WeatherStation implements Subject {
    private ArrayList<Observer> observers;
    WeatherData weatherData;
    public WeatherStation(){
        observers = new ArrayList<Observer>();
    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(weatherData);
    }

    @Override
    public void removeObserver(Observer o) {
       int idx = observers.indexOf(o);
       if (idx >= 0) observers.remove(o);
    }
    public void measurementChanged(){
        this.notifyObservers();
    }
    public void setMeasurement(float temperature,float pressure,float humidity){
        weatherData = new WeatherData(temperature,humidity,pressure);
        notifyObservers();
    }
    public void setMeasurement(){
        weatherData = new WeatherData();
    }
}
