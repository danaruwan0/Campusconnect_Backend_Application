package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.Weather.WeatherResponse;
import com.hivex.campusconnect.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherResponse getWeather(
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {

        return weatherService.getWeather(latitude, longitude);

    }

}