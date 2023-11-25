package com.klusterthon.Smartfarm.model.response;

import com.klusterthon.Smartfarm.model.entity.Farmer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResponse {
    private Long id;
    private String prediction;
    private String crop;
//    private String plantSzn;
//    private String harvSzn;
    private LocalDateTime createdAt;
}
