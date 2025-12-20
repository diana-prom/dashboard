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

    @OneToOne
    @MapsId
    @JoinColumn(name = "food_nutrient_conversion_factor_id")
    private FoodNutrientConversionFactor nutrientConversionFactor;

    public BigDecimal getProteinValue(){
        return proteinValue;
    }

    public BigDecimal getFatValue(){
        return fatValue;
    }
    public BigDecimal getCarbohydrateValue() {
        return carbohydrateValue;
    }

    public FoodNutrientConversionFactor getNutrientConversionFactor() {
        return nutrientConversionFactor;
    }
}


