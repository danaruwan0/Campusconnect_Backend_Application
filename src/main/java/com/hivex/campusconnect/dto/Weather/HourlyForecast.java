package com.hivex.campusconnect.dto.Weather;

import lombok.Data;

@Data
public class HourlyForecast {

    private String time;
    private double temperature;
    private int weatherCode;

    private String icon;

}