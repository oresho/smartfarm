package com.klusterthon.Smartfarm.service.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.model.request.FarmYieldRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(double latitude, double longitude) throws JsonProcessingException;
    WeatherResponse getWeatherDetails(String location) throws JsonProcessingException;
}
