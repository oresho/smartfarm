package com.klusterthon.Smartfarm.service.prediction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klusterthon.Smartfarm.exceptionHandler.ResourceNotFoundException;
import com.klusterthon.Smartfarm.model.entity.Farmer;
import com.klusterthon.Smartfarm.model.entity.Prediction;
import com.klusterthon.Smartfarm.model.repository.PredictionRepository;
import com.klusterthon.Smartfarm.model.request.FarmYieldRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.PredictionResponse;
import com.klusterthon.Smartfarm.model.response.WeatherResponse;
import com.klusterthon.Smartfarm.service.auth.AuthenticationService;
import com.klusterthon.Smartfarm.service.weather.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {
    private final PredictionRepository predictionRepository;
    private final AuthenticationService authenticationService;
    private final WeatherService weatherService;
    private final WebClient client;

    public PredictionServiceImpl(PredictionRepository predictionRepository, AuthenticationService authenticationService, WeatherService weatherService, WebClient.Builder webClientBuilder) {
        this.predictionRepository = predictionRepository;
        this.authenticationService = authenticationService;
        this.weatherService = weatherService;
        this.client = webClientBuilder.baseUrl("https://pelvic23.pythonanywhere.com").build();
    }

    @Override
    public ApiResponseDto<?> getFarmerPredictions(Long farmerId) {
        List<Prediction> prediction = predictionRepository.findAllByFarmerId(farmerId);
        List<PredictionResponse> predictionResponses = prediction.stream().map(this::mapToPredictionResponse).toList();
        return new ApiResponseDto<>(
                "Successfully gotten farmer's predictions",
                HttpStatus.OK.value(),
                predictionResponses
        );
    }

    @Override
    public ApiResponseDto<?> getHarvestPrediction(FarmYieldRequest farmYieldRequest) throws JsonProcessingException {
        WeatherResponse weatherResponse = weatherService.getWeatherDetails(farmYieldRequest.getLocation());
        String jsonResponse = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/predict")
                        .queryParam("temperature", weatherResponse.getMain().getTemp())
                        .queryParam("humidity", weatherResponse.getMain().getHumidity())
                        .queryParam("ph", farmYieldRequest.getPh())
                        .queryParam("water_availability", farmYieldRequest.getWaterAvailability())
                        .queryParam("label", farmYieldRequest.getLabel())
                        .queryParam("country", farmYieldRequest.getLocation())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String cleanedString = jsonNode.toString().replaceAll("[\\\\\"{}]", "");
        PredictionResponse predictionResponse = mapToPredictionResponse(savePrediction(cleanedString, farmYieldRequest.getLabel()));
        return new ApiResponseDto<>("Successfully got prediction",
                HttpStatus.OK.value(),
                predictionResponse);
    }

    @Override
    public ApiResponseDto<?> getPredictionById(Long id) {
        Farmer farmer = authenticationService.getLoggedInFarmer();
        Prediction prediction = predictionRepository.findByIdAndFarmerId(id, farmer.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No prediction with this ID"));
        PredictionResponse predictionResponse = mapToPredictionResponse(prediction);
        return new ApiResponseDto<>("Successfully got prediction",
                HttpStatus.OK.value(),
                predictionResponse);
    }

    private Prediction savePrediction(String jsonResponse, String crop) {
        Prediction prediction = new Prediction();
        prediction.setPrediction(jsonResponse);
        prediction.setCrop(crop);
        prediction.setCreatedAt(LocalDateTime.now());
        prediction.setFarmer(authenticationService.getLoggedInFarmer());
        return predictionRepository.save(prediction);
    }

    private PredictionResponse mapToPredictionResponse(Prediction prediction){
        PredictionResponse predictionResponse = new PredictionResponse();
        predictionResponse.setId(prediction.getId());
        predictionResponse.setPrediction(prediction.getPrediction());
        predictionResponse.setCrop(prediction.getCrop());
//        predictionResponse.setPlantSzn();
//        predictionResponse.setHarvSzn();
        predictionResponse.setCreatedAt(prediction.getCreatedAt());
        return predictionResponse;
    }
}
