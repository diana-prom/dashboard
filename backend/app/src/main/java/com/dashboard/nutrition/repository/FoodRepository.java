package com.dashboard.nutrition.repository;

import com.dashboard.nutrition.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    @Query("SELECT DISTINCT f " +
        "FROM Food f " +
        "LEFT JOIN FETCH f.nutrientConversionFactors n " +
        "LEFT JOIN FETCH n.calorieConversionFactor c " +
        "WHERE LOWER(f.description) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Food> searchWithNutrients(@Param("name")String name);

    List<Food> findByDescriptionContainingIgnoreCase(String description);
    Optional<Food> findFirstByDescriptionContainingIgnoreCase(String description);

}

