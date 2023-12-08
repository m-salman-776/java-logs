package DesignPatterns.wsobserver.repository.dbImpl;

import DesignPatterns.wsobserver.models.classes.WeatherData;
import DesignPatterns.wsobserver.repository.dbInterfaces.DataInterface;

import java.util.ArrayList;
import java.util.List;


public class DbMock implements DataInterface <WeatherData> {
    ArrayList<WeatherData> data ;
    public DbMock(){
        System.out.println("Initialized");
        data = new ArrayList<>();
    }
    @Override
    public int getSize(){
        return this.data.size();
    }
    @Override
    public WeatherData getById(long id) {
        for (WeatherData weatherData : data) {
            if (weatherData.id == id) return weatherData;
        }
        return null;
    }

    @Override
    public List<WeatherData> getAll() {
        return this.data;
    }

    @Override
    public void insert(WeatherData weatherData) {
        this.data.add(weatherData);
    }

    @Override
    public void update(WeatherData weatherData, String[] param) {
        WeatherData weatherData1 =null;
        for (WeatherData obj : data) {
            if (obj.id == weatherData.id) {

            }
        }
    }

    @Override
    public void delete(WeatherData weatherData) {
        int idx = data.indexOf(weatherData);
        if (idx >= 0) data.remove(weatherData);
    }
}
