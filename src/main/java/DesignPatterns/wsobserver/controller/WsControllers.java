package DesignPatterns.wsobserver.controller;

import DesignPatterns.wsobserver.models.classes.WeatherData;
import DesignPatterns.wsobserver.repository.dbInterfaces.DataInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/ws", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class WsControllers {
    @Autowired
    private DataInterface wsData;
    @GetMapping("/{id}")
    public ResponseEntity<WeatherData> getObserverById(@PathVariable(value = "id") long id){
       WeatherData res = (WeatherData) wsData.getById(id);
       if (res != null) return new ResponseEntity<>(res, HttpStatus.OK);
      return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @PostMapping(path = "/add",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addWeatherData (@RequestBody WeatherData weatherData){
        wsData.insert(weatherData);
        return new ResponseEntity<>("Count : "+wsData.getSize(),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<WeatherData>> getAll(){
       List<WeatherData> wData = wsData.getAll();
       if (wData.isEmpty()) {
           return new ResponseEntity<>(wData,HttpStatus.NO_CONTENT);
       }
       return new  ResponseEntity<>(wData,HttpStatus.OK);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<WeatherData> update(@PathVariable long id , @RequestBody WeatherData newWeatherData){
        WeatherData weatherData = (WeatherData) wsData.getById(id);
        if (weatherData==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        wsData.delete(weatherData);
        wsData.insert(newWeatherData);
        return new ResponseEntity<>(weatherData,HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WeatherData> delete(@PathVariable long id){
        WeatherData weatherData = (WeatherData) wsData.getById(id);
        if (weatherData == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        wsData.delete(weatherData);
        return new ResponseEntity<>(weatherData,HttpStatus.OK);
    }
}