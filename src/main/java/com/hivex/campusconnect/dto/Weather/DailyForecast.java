package com.hivex.campusconnect.dto.Weather;

import lombok.Data;

@Data
public class DailyForecast {

    private String date;
    private double maxTemp;
    private double minTemp;
    private int weatherCode;

    private String icon;
    private String description;

}