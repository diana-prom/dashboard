package com.dashboard.nutrition.service;


import com.dashboard.nutrition.dto.FoodPortionDTO;
import com.dashboard.nutrition.entity.FoodPortion;
import com.dashboard.nutrition.entity.MeasureUnit;
import com.dashboard.nutrition.repository.FoodPortionRepository;
import com.dashboard.nutrition.repository.MeasureUnitRepository;
import org.springframework.transaction.annotation.Transactional;
import com.dashboard.nutrition.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
public class FoodPortionService {

    private final FoodPortionRepository portionRepository;
    private final MeasureUnitRepository measureUnitRepository;

    public FoodPortionService(FoodPortionRepository portionRepository,
                              MeasureUnitRepository measureUnitRepository) {
        this.portionRepository = portionRepository;
        this.measureUnitRepository = measureUnitRepository;
    }

    public FoodPortionDTO getPortionByFood(Integer fdcId) {

        FoodPortion portion = portionRepository.findFirstByFdcId(fdcId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Food portion not found for fdcId=" + fdcId));

        MeasureUnit unit = measureUnitRepository.findById(portion.getMeasureUnitId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Measure unit not found"));

        return new FoodPortionDTO(
                portion.getAmount(),
                portion.getGramWeight(),
                unit.getName()
        );
    }
}

