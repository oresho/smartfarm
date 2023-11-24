package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByPhoneNo(String phoneNo);
    Optional<Farmer> findByEmail(String email);
}
