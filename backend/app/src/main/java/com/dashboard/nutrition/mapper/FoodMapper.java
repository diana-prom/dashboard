package com.dashboard.nutrition.mapper;

import com.dashboard.nutrition.dto.FoodDTO;
import com.dashboard.nutrition.entity.Food;
import com.dashboard.nutrition.entity.FoodCalorieConversionFactor;
import com.dashboard.nutrition.entity.FoodNutrientConversionFactor;

import java.util.Comparator;
import java.util.Optional;

public class FoodMapper {

    public static FoodDTO toDTO(Food food) {
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

        Double protein = null, fat = null, carb = null;
        if (bestNutrient.isPresent()) {
            FoodCalorieConversionFactor c = bestNutrient.get().getCalorieConversionFactor();
            protein = c.getProteinValue();
            fat = c.getFatValue();
            carb = c.getCarbohydrateValue();
        }

        return new FoodDTO(
                food.getDescription(),
                protein,
                fat,
                carb
        );
    }
}
