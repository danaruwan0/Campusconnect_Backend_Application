package com.hivex.campusconnect.dto.Weather;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    private CurrentWeather current;

    private List<HourlyForecast> hourly;

    private List<DailyForecast> daily;

}