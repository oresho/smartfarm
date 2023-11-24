package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByTokenAndFarmerEmail(String token, String farmerEmail);
}
