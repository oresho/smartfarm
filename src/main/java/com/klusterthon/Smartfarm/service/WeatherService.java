package com.klusterthon.Smartfarm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;

public interface WeatherService {
    ApiResponseDto<?> getWeather(double latitude, double longitude) throws JsonProcessingException;
    ApiResponseDto<?> getWeatherDetails(String location);
}
