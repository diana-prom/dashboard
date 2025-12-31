package com.dashboard.nutrition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dashboard.nutrition.entity.FoodPortion;

import java.util.Optional;

@Repository
public interface FoodPortionRepository extends JpaRepository<FoodPortion, Integer> {
    Optional<FoodPortion> findFirstByFdcId(Integer fdcId);
}
