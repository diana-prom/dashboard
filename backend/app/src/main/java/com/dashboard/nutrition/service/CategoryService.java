package com.dashboard.nutrition.service;

import com.dashboard.nutrition.dto.CategoryDTO;
import com.dashboard.nutrition.entity.FoodCategory;
import com.dashboard.nutrition.exception.ResourceNotFoundException;
import com.dashboard.nutrition.mapper.CategoryMapper;
import com.dashboard.nutrition.repository.FoodCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CategoryService {

    private final FoodCategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(FoodCategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO findCategoryCode(Integer code) {
        FoodCategory category = categoryRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with code: " + code
                ));

        return categoryMapper.toDto(category);
    }
}

