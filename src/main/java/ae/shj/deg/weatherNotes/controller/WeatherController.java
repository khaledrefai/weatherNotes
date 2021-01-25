package ae.shj.deg.weatherNotes.controller;

import ae.shj.deg.weatherNotes.model.CurrentWeather;
import ae.shj.deg.weatherNotes.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@RestController
public class WeatherController {


    private final  WeatherService weatherService;

    WeatherController(WeatherService weatherService){
        this.weatherService =  weatherService;
    }

    @Autowired
    @GetMapping("/api/current-weather")
    public CurrentWeather getCurrentWeather() {
        CurrentWeather currentWeather = weatherService.getCurrentWeather();
         return currentWeather;
    }
}
