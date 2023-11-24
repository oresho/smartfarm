package com.klusterthon.Smartfarm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Operation(summary = "Get weather details using lat and lon")
    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam double latitude, @RequestParam double longitude) throws JsonProcessingException {
        HttpHeaders headers = getHttpHeaders();
        return new ResponseEntity<>(
                weatherService.getWeather(latitude,longitude),
                HttpStatus.OK
        );
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "*");
        return headers;
    }

    @Operation(summary = "Get weather details using country location")
    @GetMapping("/details")
    public ResponseEntity<?> getWeatherDetails(@RequestParam String location) throws JsonProcessingException {
        HttpHeaders headers = getHttpHeaders();
        return new ResponseEntity<>(
                weatherService.getWeatherDetails(location),
                HttpStatus.OK
        );
    }


}
