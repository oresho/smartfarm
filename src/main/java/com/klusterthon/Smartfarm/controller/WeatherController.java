package com.klusterthon.Smartfarm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.model.request.FarmYieldRequest;
import com.klusterthon.Smartfarm.service.WeatherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {
    private final WeatherService weatherService;
    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam double latitude, @RequestParam double longitude) throws JsonProcessingException {
        return new ResponseEntity<>(
                weatherService.getWeather(latitude,longitude),
                HttpStatus.OK
        );
    }

    @GetMapping("/details")
    public ResponseEntity<?> getWeatherDetails(@RequestParam String location) throws JsonProcessingException {
        return new ResponseEntity<>(
                weatherService.getWeatherDetails(location),
                HttpStatus.OK
        );
    }

    @GetMapping("/yield-prediction")
    public ResponseEntity<?> getPrediction(@RequestParam String label,
                                           @RequestParam String location,
                                           @RequestParam double ph,
                                           @RequestParam double water_availability) throws JsonProcessingException {
        FarmYieldRequest farmYieldRequest = new FarmYieldRequest();
        farmYieldRequest.setPh(ph);
        farmYieldRequest.setLocation(location);
        farmYieldRequest.setLabel(label);
        farmYieldRequest.setWaterAvailability(water_availability);
        return new ResponseEntity<>(weatherService.getHarvestPrediction(farmYieldRequest), HttpStatus.OK);
    }
}
