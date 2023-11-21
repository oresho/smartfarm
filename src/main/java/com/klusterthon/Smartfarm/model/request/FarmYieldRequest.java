package com.klusterthon.Smartfarm.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmYieldRequest {
    @NotBlank(message = "location is required")
    private String location;
    @NotBlank(message = "ph is required")
    private double ph;
    @NotBlank(message = "waterAvailability is required")
    private double waterAvailability;
}
