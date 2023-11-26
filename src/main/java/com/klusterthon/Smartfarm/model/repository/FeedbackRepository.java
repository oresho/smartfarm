package com.klusterthon.Smartfarm.model.repository;

import com.klusterthon.Smartfarm.model.entity.FarmerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FarmerFeedback, Long> {
}
