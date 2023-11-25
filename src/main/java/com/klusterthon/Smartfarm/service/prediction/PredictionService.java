package com.klusterthon.Smartfarm.service.prediction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.model.request.FarmYieldRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;

public interface PredictionService {
    ApiResponseDto<?> getFarmerPredictions(Long farmerId);
    ApiResponseDto<?> getHarvestPrediction(FarmYieldRequest farmYieldRequest) throws JsonProcessingException;

    ApiResponseDto<?> getPredictionById(Long id);

}
