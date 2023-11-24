package com.klusterthon.Smartfarm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klusterthon.Smartfarm.exceptionHandler.ApplicationException;
import com.klusterthon.Smartfarm.model.response.WeatherResponse;
import com.klusterthon.Smartfarm.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final WebClient webClient;
    @Value("${openweathermap.api.key}")
    private String apiKey;

    // Inject WebClient into the constructor
    public WeatherServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    @Override
    public WeatherResponse getWeather(double latitude, double longitude) throws JsonProcessingException {
        return  getWeatherResponse(latitude,longitude);
    }

    @Override
    public WeatherResponse getWeatherDetails(String location) throws JsonProcessingException {
        return switch (location) {
            case "South Africa" -> getWeather(-30.5595, 22.9375);
            case "Kenya" -> getWeather(0.0236, 37.9062);
            case "Nigeria" -> getWeather(9.0820, 8.6753);
            default ->
                    throw new ApplicationException("AI cannot generate weather details for this location: " + location);
        };
    }

    private WeatherResponse getWeatherResponse(double latitude, double longitude) throws JsonProcessingException {
        String endpoint = "/weather";
        String jsonResponse = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(endpoint)
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("appid", apiKey)
                        .queryParam("callback", "test")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        // Remove the "test(" and ")" from the JSON string
        jsonResponse = jsonResponse.substring(5, jsonResponse.length() - 1);
        // Parse JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, WeatherResponse.class);
    }
}
