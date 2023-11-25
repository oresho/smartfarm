package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findAllByFarmerId(Long farmerId);

    Optional<Prediction> findByIdAndFarmerId(Long id, Long farmerId);
}
