package com.klusterthon.Smartfarm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.klusterthon.Smartfarm.model.request.FarmYieldRequest;
import com.klusterthon.Smartfarm.service.auth.AuthenticationService;
import com.klusterthon.Smartfarm.service.prediction.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prediction")
//@CrossOrigin(origins = {"*", "http://localhost:3000/"})
//@CrossOrigin("*")
public class PredictionController {
    private final PredictionService predictionService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Get all predictions by logged in farmer")
//    @GetMapping()
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllPredictions(){
        return new ResponseEntity<>(predictionService.getFarmerPredictions(authenticationService.getLoggedInFarmer().getId()),
//                getHttpHeaders(),
                HttpStatus.OK);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "*");
        return headers;
    }

    @Operation(summary = "Get yield prediction")
//    @GetMapping("/yield-prediction")
    @RequestMapping(value = "/yield-prediction", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getPrediction(@RequestParam String label,
                                           @RequestParam String location,
                                           @RequestParam double ph,
                                           @RequestParam double water_availability) throws JsonProcessingException {
        FarmYieldRequest farmYieldRequest = getFarmYieldRequest(label, location, ph, water_availability);
        return new ResponseEntity<>(predictionService.getHarvestPrediction(farmYieldRequest),
//                getHttpHeaders(),
                HttpStatus.OK);
    }

    private static FarmYieldRequest getFarmYieldRequest(String label, String location, double ph, double water_availability) {
        FarmYieldRequest farmYieldRequest = new FarmYieldRequest();
        farmYieldRequest.setPh(ph);
        farmYieldRequest.setLocation(location);
        farmYieldRequest.setLabel(label);
        farmYieldRequest.setWaterAvailability(water_availability);
        return farmYieldRequest;
    }
}
