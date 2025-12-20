package com.dashboard.nutrition.repository;

import com.dashboard.nutrition.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByDescriptionContainingIgnoreCase(String description);
    Optional<Food> findFirstByDescriptionContainingIgnoreCase(String description);
}

