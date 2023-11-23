package com.klusterthon.Smartfarm.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmYieldRequest {
    @NotBlank(message = "crop is required")
    private String label;
    @NotBlank(message = "country is required")
    private String location;
    @NotNull(message = "ph is required")
    private double ph;
    @NotNull(message = "waterAvailability is required")
    private double waterAvailability;
}
