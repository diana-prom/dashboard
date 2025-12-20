package com.dashboard.nutrition.controller;

import com.dashboard.nutrition.entity.Food;
import com.dashboard.nutrition.service.FoodService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/search")
    public Food getFoodMacros(@RequestParam String name){
        return foodService.getFoodWithMacros(name);
    }
}
