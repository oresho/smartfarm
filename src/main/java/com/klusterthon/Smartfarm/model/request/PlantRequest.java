package com.klusterthon.Smartfarm.model.request;

import com.klusterthon.Smartfarm.model.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantRequest {
    @NotBlank(message = "crop is required")
    private String crop;
    @NotBlank(message = "plantedSzn is required")
    private String plantedSzn;
    @NotBlank(message = "harvestSzn is required")
    private String harvestSzn;
//    @NotNull(message = "status is required")
//    private Status status;
}