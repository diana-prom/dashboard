package com.dashboard.nutrition.mapper;

import com.dashboard.nutrition.entity.FoodCategory;
import com.dashboard.nutrition.dto.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDto(FoodCategory category) {
        if (category == null) return null;

        return new CategoryDTO(
                category.getId(),
                category.getCode(),
                category.getDescription()
        );
    }
}

