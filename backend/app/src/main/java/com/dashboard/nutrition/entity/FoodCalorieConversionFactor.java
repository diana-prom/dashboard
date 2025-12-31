package com.dashboard.nutrition.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "food_calorie_conversion_factor")
public class FoodCalorieConversionFactor {

    @Id
    @Column(name = "food_nutrient_conversion_factor_id")
    private Integer id;

    @Column(name = "protein_value")
    private BigDecimal proteinValue;

    @Column(name = "fat_value")
    private BigDecimal fatValue;

    @Column(name = "carbohydrate_value")
    private BigDecimal carbohydrateValue;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "food_nutrient_conversion_factor_id")
    private FoodNutrientConversionFactor nutrientConversionFactor;

    public FoodCalorieConversionFactor() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getProteinValue() { return proteinValue; }

    public void setProteinValue(BigDecimal proteinValue) { this.proteinValue = proteinValue; }

    public BigDecimal getFatValue() { return fatValue; }

    public void setFatValue(BigDecimal fatValue) { this.fatValue = fatValue; }

    public BigDecimal getCarbohydrateValue() { return carbohydrateValue; }

    public void setCarbohydrateValue(BigDecimal carbohydrateValue) {
        this.carbohydrateValue = carbohydrateValue;
    }

    public FoodNutrientConversionFactor getNutrientConversionFactor() {
        return nutrientConversionFactor;
    }

    public void setNutrientConversionFactor(FoodNutrientConversionFactor nutrientConversionFactor) {
        this.nutrientConversionFactor = nutrientConversionFactor;
    }
}
