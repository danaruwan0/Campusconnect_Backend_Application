package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.Weather.CurrentWeather;
import com.hivex.campusconnect.dto.Weather.DailyForecast;
import com.hivex.campusconnect.dto.Weather.HourlyForecast;
import com.hivex.campusconnect.dto.Weather.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(double latitude, double longitude) {

        String url =
                "https://api.open-meteo.com/v1/forecast" +
                        "?latitude=" + latitude +
                        "&longitude=" + longitude +
                        "&current=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m" +
                        "&hourly=temperature_2m,weather_code" +
                        "&daily=weather_code,temperature_2m_max,temperature_2m_min" +
                        "&forecast_days=7";

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        WeatherResponse weather = new WeatherResponse();

        // ================= CURRENT =================

        Map<String, Object> current =
                (Map<String, Object>) response.get("current");

        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setTemperature(
                ((Number) current.get("temperature_2m")).doubleValue());

        currentWeather.setFeelsLike(
                ((Number) current.get("apparent_temperature")).doubleValue());

        currentWeather.setWindSpeed(
                ((Number) current.get("wind_speed_10m")).doubleValue());

        currentWeather.setHumidity(
                ((Number) current.get("relative_humidity_2m")).doubleValue());

        currentWeather.setWeatherCode(
                ((Number) current.get("weather_code")).intValue());

        currentWeather.setTime(
                current.get("time").toString());

        currentWeather.setIcon(
                getIcon(currentWeather.getWeatherCode()));

        currentWeather.setDescription(
                getDescription(currentWeather.getWeatherCode()));

        weather.setCurrent(currentWeather);

        // ================= HOURLY =================

        Map<String, Object> hourly =
                (Map<String, Object>) response.get("hourly");

        List<String> times =
                (List<String>) hourly.get("time");

        List<Number> temps =
                (List<Number>) hourly.get("temperature_2m");

        List<Number> codes =
                (List<Number>) hourly.get("weather_code");

        List<HourlyForecast> hourlyForecasts =
                new ArrayList<>();

        for (int i = 0; i < 24 && i < times.size(); i++) {

            HourlyForecast h = new HourlyForecast();

            h.setTime(times.get(i));
            h.setTemperature(temps.get(i).doubleValue());
            h.setWeatherCode(codes.get(i).intValue());

            h.setIcon(
                    getIcon(h.getWeatherCode()));

            hourlyForecasts.add(h);

        }

        weather.setHourly(hourlyForecasts);

        // ================= DAILY =================

        Map<String, Object> daily =
                (Map<String, Object>) response.get("daily");

        List<String> dates =
                (List<String>) daily.get("time");

        List<Number> maxTemps =
                (List<Number>) daily.get("temperature_2m_max");

        List<Number> minTemps =
                (List<Number>) daily.get("temperature_2m_min");

        List<Number> weatherCodes =
                (List<Number>) daily.get("weather_code");

        List<DailyForecast> dailyForecasts =
                new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {

            DailyForecast d = new DailyForecast();

            d.setDate(dates.get(i));
            d.setMaxTemp(maxTemps.get(i).doubleValue());
            d.setMinTemp(minTemps.get(i).doubleValue());
            d.setWeatherCode(weatherCodes.get(i).intValue());

            d.setIcon(
                    getIcon(d.getWeatherCode()));

            d.setDescription(
                    getDescription(d.getWeatherCode()));

            dailyForecasts.add(d);

        }

        weather.setDaily(dailyForecasts);

        return weather;
    }

    // ================= ICON =================

    private String getIcon(int code) {

        switch (code) {

            case 0:
                return "01d";

            case 1:
            case 2:
                return "02d";

            case 3:
                return "03d";

            case 45:
            case 48:
                return "50d";

            case 51:
            case 53:
            case 55:
                return "09d";

            case 61:
            case 63:
            case 65:
            case 80:
            case 81:
            case 82:
                return "10d";

            case 71:
            case 73:
            case 75:
            case 77:
                return "13d";

            case 95:
            case 96:
            case 99:
                return "11d";

            default:
                return "01d";
        }
    }

    // ================= DESCRIPTION =================

    private String getDescription(int code) {

        switch (code) {

            case 0:
                return "Clear Sky";

            case 1:
                return "Mainly Clear";

            case 2:
                return "Partly Cloudy";

            case 3:
                return "Cloudy";

            case 45:
            case 48:
                return "Fog";

            case 51:
            case 53:
            case 55:
                return "Drizzle";

            case 56:
            case 57:
                return "Freezing Drizzle";

            case 61:
            case 63:
            case 65:
                return "Rain";

            case 66:
            case 67:
                return "Freezing Rain";

            case 71:
            case 73:
            case 75:
                return "Snow";

            case 77:
                return "Snow Grains";

            case 80:
            case 81:
            case 82:
                return "Rain Showers";

            case 85:
            case 86:
                return "Snow Showers";

            case 95:
                return "Thunderstorm";

            case 96:
            case 99:
                return "Thunderstorm with Hail";

            default:
                return "Unknown";
        }
    }
}