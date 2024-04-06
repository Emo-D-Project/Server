package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // 사용자에게 위도와 경도를 받아 날씨 정보를 제공하는 API
    /*
    * @param lat 위도
    * @param lon 경도
     */
    @GetMapping("/weather")
    @Operation(summary = "날씨 정보 제공 lat = 위도, lon = 경도")
    public String getWeather(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.getWeatherData(lat, lon);
    }
}
