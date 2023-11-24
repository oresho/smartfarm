package com.klusterthon.Smartfarm.model.response;

import com.klusterthon.Smartfarm.model.entity.Farmer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResponse {
    private String prediction;
}
