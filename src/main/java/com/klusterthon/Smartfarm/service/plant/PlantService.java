package com.klusterthon.Smartfarm.service.plant;

import com.klusterthon.Smartfarm.model.request.PlantRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;

public interface PlantService {
    ApiResponseDto<?> create(PlantRequest plantRequest);
    ApiResponseDto<?> getPlantHistory();
}
