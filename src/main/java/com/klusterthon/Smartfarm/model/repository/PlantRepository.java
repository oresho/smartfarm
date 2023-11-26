package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findAllByFarmerId(Long farmerId);
}
