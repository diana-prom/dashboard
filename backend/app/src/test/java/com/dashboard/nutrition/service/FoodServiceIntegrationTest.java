package com.dashboard.nutrition.service;

import com.dashboard.nutrition.entity.Food;
import com.dashboard.nutrition.entity.FoodNutrientConversionFactor;
import com.dashboard.nutrition.repository.FoodRepository;
import com.dashboard.nutrition.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FoodServiceIntegrationTest {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodService foodService;

    @Test
    void testSearchFoodByNameThroughService() {
        // Seed
        Food food = new Food();
        food.setFdc_id(320085);
        food.setDescription("Paint Example");

        FoodNutrientConversionFactor factor = new FoodNutrientConversionFactor();
        factor.setProteinValue(10.0);
        factor.setFood(food);
        food.setNutrientConversionFactor(factor);

        foodRepository.save(food);

        //  use the service to search
        Food found = foodService.getFoodWithMacros("paint");

        assertNotNull(found.getDescription());
        assertNotNull(found.getNutrientConversionFactor());
        assertNotNull(found.getNutrientConversionFactor().getProteinValue());

        System.out.println("Found food: " + found.getDescription());
        System.out.println("Protein value: " + found.getNutrientConversionFactor().getProteinValue());
    }
}
