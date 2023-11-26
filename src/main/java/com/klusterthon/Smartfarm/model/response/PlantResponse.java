package com.klusterthon.Smartfarm.model.response;

import com.klusterthon.Smartfarm.model.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantResponse {
    private Long id;
    private String crop;
    private String plantedSzn;
    private String harvestSzn;
    private Status status;
    private Long farmerId;
}
