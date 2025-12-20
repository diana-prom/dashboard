package com.dashboard.nutrition.service;

import com.dashboard.nutrition.entity.Food;
import com.dashboard.nutrition.exception.ResourceNotFoundException;
import com.dashboard.nutrition.repository.FoodRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    public Food getFoodWithMacros(String name) {
        return foodRepository.findFirstByDescriptionContainingIgnoreCase(name)
                .orElseThrow(() -> {
                    System.out.println("Food not found, throwing exception for: " + name);
                    return new ResourceNotFoundException("Food not found");
                });

    }
      public List<Food> searchFoodsWithMacros(String name) {
          List<Food> foods = foodRepository.findByDescriptionContainingIgnoreCase(name);
          if(foods.isEmpty()){
              System.out.println("No foods found for: " + name );
              throw new ResourceNotFoundException("No foods found");
          }
          return foods;
        }
    }

