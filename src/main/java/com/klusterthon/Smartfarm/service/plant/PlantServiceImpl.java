package com.klusterthon.Smartfarm.service.plant;

import com.klusterthon.Smartfarm.model.entity.Plant;
import com.klusterthon.Smartfarm.model.entity.Status;
import com.klusterthon.Smartfarm.model.repository.PlantRepository;
import com.klusterthon.Smartfarm.model.request.PlantRequest;
import com.klusterthon.Smartfarm.model.response.ApiResponseDto;
import com.klusterthon.Smartfarm.model.response.PlantResponse;
import com.klusterthon.Smartfarm.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService{
    private final PlantRepository plantRepository;
    private final AuthenticationService authenticationService;
    @Override
    public ApiResponseDto<?> create(PlantRequest plantRequest) {
        Plant plant = mapToPlant(plantRequest);
        Plant savedPlant = plantRepository.save(plant);
        return new ApiResponseDto<>(
                "Successfully Created plant record",
                HttpStatus.CREATED.value(),
                mapToPlantResponse(savedPlant)
        );
    }

    @Override
    public ApiResponseDto<?> getPlantHistory() {
        List<Plant> plantList = plantRepository.findAllByFarmerId(authenticationService.getLoggedInFarmer().getId());
        List<PlantResponse> plantResponseList = plantList.stream().map(this::mapToPlantResponse).toList();
        return new ApiResponseDto<>(
                "Successfully Gotten plant record",
                HttpStatus.CREATED.value(),
                plantResponseList
        );
    }

    private Plant mapToPlant(PlantRequest plantRequest){
        Plant plant = new Plant();
        plant.setCrop(plantRequest.getCrop());
        plant.setPlantedSzn(plantRequest.getPlantedSzn());
        plant.setHarvestSzn(plantRequest.getHarvestSzn());
        plant.setStatus(Status.ONGOING);
        plant.setFarmer(authenticationService.getLoggedInFarmer());
        return plant;
    }

    private PlantResponse mapToPlantResponse(Plant plant){
        PlantResponse plantResponse = new PlantResponse();
        plantResponse.setId(plant.getId());
        plantResponse.setCrop(plant.getCrop());
        plantResponse.setPlantedSzn(plant.getPlantedSzn());
        plantResponse.setHarvestSzn(plant.getHarvestSzn());
        plantResponse.setStatus(plant.getStatus());
        plantResponse.setFarmerId(authenticationService.getLoggedInFarmer().getId());
        return plantResponse;
    }
}
