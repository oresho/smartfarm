package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
