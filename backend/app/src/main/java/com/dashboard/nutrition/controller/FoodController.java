package com.dashboard.nutrition.controller;

import com.dashboard.nutrition.dto.FoodDTO;
import com.dashboard.nutrition.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * Get all foods
     */
    @GetMapping
    public List<FoodDTO> getAllFoods() {
        return foodService.getAllFoods();
    }

    /**
     * Get a single food by ID
     */
    @GetMapping("/{fdcId}")
    public FoodDTO getFoodById(@PathVariable Integer fdcId) {
        return foodService.getFoodById(fdcId);
    }

    /**
     * Search best food by description
     * Example: /api/food/search/best?name=cheddar cheese
     */
    @GetMapping("/search/best")
    public FoodDTO searchBest(@RequestParam String name) {
        return foodService.searchBestByDescription(name);
    }
}
