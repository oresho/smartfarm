package com.klusterthon.Smartfarm.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    // Getters and setters

    @Data
    public static class Coord {
        private double lon;
        private double lat;

        // Getters and setters
    }

    @Data
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        // Getters and setters
    }

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;

        // Getters and setters
    }

    @Data
    public static class Wind {
        private double speed;
        private double deg;
        private double gust;

        // Getters and setters
    }

    @Data
    public static class Clouds {
        private int all;

        // Getters and setters
    }

    @Data
    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;

        // Getters and setters
    }
}
