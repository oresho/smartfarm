package com.klusterthon.Smartfarm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.service.weather.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
//@CrossOrigin(origins = {"*", "http://localhost:3000/"})
//@CrossOrigin("*")
public class WeatherController {
    private final WeatherService weatherService;

    @Operation(summary = "Get weather details using lat and lon")
    //    @GetMapping()
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getWeather(@RequestParam double latitude, @RequestParam double longitude) throws JsonProcessingException {
        return new ResponseEntity<>(
                weatherService.getWeather(latitude,longitude),
                getHttpHeaders(),
                HttpStatus.OK
        );
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "*");
        return headers;
    }

    @Operation(summary = "Get weather details using country location")
//    @GetMapping("/details")
    @RequestMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getWeatherDetails(@RequestParam String location) throws JsonProcessingException {
        return new ResponseEntity<>(
                weatherService.getWeatherDetails(location),
                getHttpHeaders(),
                HttpStatus.OK
        );
    }

}
