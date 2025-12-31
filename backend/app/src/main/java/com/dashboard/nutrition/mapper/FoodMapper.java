package com.dashboard.nutrition.mapper;

import com.dashboard.nutrition.dto.FoodDTO;
import com.dashboard.nutrition.entity.Food;
import com.dashboard.nutrition.entity.FoodCalorieConversionFactor;
import com.dashboard.nutrition.entity.FoodCategory;
import com.dashboard.nutrition.entity.FoodNutrientConversionFactor;
import com.dashboard.nutrition.repository.FoodCategoryRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;


@Component
public class FoodMapper {

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodMapper(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }

    public FoodDTO toDTO(Food food) {
        if (food == null) return null;

        // Pick the "best" nutrient row
        Optional<FoodNutrientConversionFactor> bestNutrient = food.getNutrientConversionFactors().stream()
                .filter(n -> n.getCalorieConversionFactor() != null)
                .max(Comparator.comparingInt(n -> {
                    FoodCalorieConversionFactor c = n.getCalorieConversionFactor();
                    int count = 0;
                    if (c.getProteinValue() != null) count++;
                    if (c.getFatValue() != null) count++;
                    if (c.getCarbohydrateValue() != null) count++;
                    return count;
                }));

        BigDecimal protein = null, fat = null, carb = null;

        if (bestNutrient.isPresent()) {
            FoodCalorieConversionFactor c = bestNutrient.get().getCalorieConversionFactor();
            protein = c.getProteinValue();
            fat = c.getFatValue();
            carb = c.getCarbohydrateValue();
        }

        // Category lookup (FK -> table)
        Integer foodCategoryId = food.getFoodCategoryId();

        String category = null;
        if (foodCategoryId != null) {
            category = foodCategoryRepository.findById(foodCategoryId)
                    .map(FoodCategory::getDescription)
                    .orElse(null);
        }

        return new FoodDTO(
                food.getFdcId(),
                food.getDescription(),
                protein,
                fat,
                carb,
                foodCategoryId,
                category
        );
    }
}
