package com.dashboard.nutrition.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food_calorie_conversion_factor")
public class FoodCalorieConversionFactor {

    @Id
    @Column(name = "food_nutrient_conversion_factor_id")
    private Integer id;

    @Column(name = "protein_value")
    private Double proteinValue;

    @Column(name = "fat_value")
    private Double fatValue;

    @Column(name = "carbohydrate_value")
    private Double carbohydrateValue;

    @OneToOne
    @MapsId
    @JoinColumn(name = "food_nutrient_conversion_factor_id")
    private FoodNutrientConversionFactor nutrientConversionFactor;

    public FoodCalorieConversionFactor() {
    }

    public Double getProteinValue() {
        return proteinValue;
    }

    public void setProteinValue(Double proteinValue) {
        this.proteinValue = proteinValue;
    }

    public Double getFatValue() {
        return fatValue;
    }

    public void setFatValue(Double fatValue) {
        this.fatValue = fatValue;
    }

    public Double getCarbohydrateValue() {
        return carbohydrateValue;
    }

    public void setCarbohydrateValue(Double carbohydrateValue) {
        this.carbohydrateValue = carbohydrateValue;
    }

    public FoodNutrientConversionFactor getNutrientConversionFactor() {
        return nutrientConversionFactor;
    }

    public void setNutrientConversionFactor(FoodNutrientConversionFactor nutrientConversionFactor) {
        this.nutrientConversionFactor = nutrientConversionFactor;
    }

    public void setCalorieConversionFactor(FoodCalorieConversionFactor c) {
    }
}
