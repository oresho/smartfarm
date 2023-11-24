package com.klusterthon.Smartfarm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.model.entity.Farmer;
import com.klusterthon.Smartfarm.model.repository.PredictionRepository;
import com.klusterthon.Smartfarm.model.request.FarmYieldRequest;
import com.klusterthon.Smartfarm.service.AuthenticationService;
import com.klusterthon.Smartfarm.service.PredictionService;
import com.klusterthon.Smartfarm.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prediction")
@CrossOrigin(origins = "*")
public class PredictionController {
    private final PredictionService predictionService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Get all predictions by logged in farmer")
    @GetMapping()
    public ResponseEntity<?> getAllPredictions(){
        return new ResponseEntity<>(predictionService.getFarmerPredictions(authenticationService.getLoggedInFarmer().getId()), HttpStatus.OK);
    }

    @Operation(summary = "Get yield prediction")
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
        return new ResponseEntity<>(predictionService.getHarvestPrediction(farmYieldRequest), HttpStatus.OK);
    }
}