package com.hivex.campusconnect.dto.Weather;

import lombok.Data;

@Data
public class CurrentWeather {

    private double temperature;
    private double feelsLike;
    private double windSpeed;
    private int weatherCode;
    private double humidity;
    private String time;

    private String icon;
    private String description;




}