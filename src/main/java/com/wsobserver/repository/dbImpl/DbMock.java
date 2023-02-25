package com.wsobserver.repository.dbImpl;

import com.wsobserver.models.classes.WeatherData;
import com.wsobserver.repository.dbInterfaces.DataInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DbMock implements DataInterface <WeatherData> {
    ArrayList<WeatherData> data ;
    public DbMock(){
        System.out.println("Initialized");
        data = new ArrayList<>();
    }
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
