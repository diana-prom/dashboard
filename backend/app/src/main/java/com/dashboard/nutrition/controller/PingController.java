package com.dashboard.nutrition.controller;

import com.dashboard.nutrition.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    public PingController() {
    }

    @GetMapping({"/ping"})
    public String pingDatabase() {
        try {
            long count = this.foodCategoryRepository.count();
            return "Database is connected! Total food categories: " + count;
        } catch (Exception var3) {
            return "Database connection failed: " + var3.getMessage();
        }
    }
}
