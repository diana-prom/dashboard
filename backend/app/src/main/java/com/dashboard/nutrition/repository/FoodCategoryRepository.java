package com.dashboard.nutrition.repository;

import com.dashboard.nutrition.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {
   Optional<FoodCategory> findByCode(Integer code);
}