package com.dashboard.nutrition.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @Column(name = "fdc_id")
    private Integer fdcId;

    private String description;

    @Column(name = "food_category_id")
    private Integer foodCategoryId;

    @JsonManagedReference
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodNutrientConversionFactor> nutrientConversionFactors = new ArrayList<>();

    public Integer getFdcId() {
        return fdcId;
    }

    public void setFdcId(Integer fdcId) {
        this.fdcId = fdcId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(Integer foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public List<FoodNutrientConversionFactor> getNutrientConversionFactors() {
        return nutrientConversionFactors;
    }

    public void setNutrientConversionFactors(List<FoodNutrientConversionFactor> nutrientConversionFactors) {
        this.nutrientConversionFactors = nutrientConversionFactors;
    }

    public void addNutrientConversionFactor(FoodNutrientConversionFactor nutrient) {
        nutrientConversionFactors.add(nutrient);
        nutrient.setFood(this);
    }
}
