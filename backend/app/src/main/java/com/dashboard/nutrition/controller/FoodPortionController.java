package com.dashboard.nutrition.controller;

import com.dashboard.nutrition.dto.FoodPortionDTO;
import com.dashboard.nutrition.service.FoodPortionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/food")
public class FoodPortionController {

    private final FoodPortionService portionService;

    public FoodPortionController(FoodPortionService portionService) {
        this.portionService = portionService;
    }

    @GetMapping("/portion")
    public FoodPortionDTO getFoodPortion(@RequestParam("fdc_id") Integer fdcId) {
        return portionService.getPortionByFood(fdcId);
    }
}

