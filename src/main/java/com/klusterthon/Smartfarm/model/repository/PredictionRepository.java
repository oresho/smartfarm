package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findAllByFarmerId(Long farmerId);
}
