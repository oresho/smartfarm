package com.klusterthon.Smartfarm.controller;

import com.klusterthon.Smartfarm.model.request.PlantRequest;
import com.klusterthon.Smartfarm.service.plant.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plant")
public class PlantController {
    private final PlantService plantService;

    @Operation(summary = "Get plant tracking record of logged in farmer")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllTrackingHistory(){
        return new ResponseEntity<>(plantService.getPlantHistory(), HttpStatus.OK);
    }

    @Operation(summary = "Create plant tracking record of logged in farmer")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> addNewPlant(@Valid @RequestBody PlantRequest plantRequest){
        return new ResponseEntity<>(plantService.create(plantRequest), HttpStatus.OK);
    }
}
