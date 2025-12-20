package com.dashboard.nutrition.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;



@Entity
@Table(name = "food_nutrient_conversion_factor")
public class FoodNutrientConversionFactor {

    @Id
    private Integer id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "fdc_id", nullable = false)
    private Food food;

    @OneToOne(mappedBy = "nutrientConversionFactor", fetch = FetchType.LAZY)
    private FoodCalorieConversionFactor calorieConversionFactor;

    public Integer getId() { return id;}
    public Food getFood() { return food;}

    public FoodCalorieConversionFactor getCalorieConversionFactor() {

        return calorieConversionFactor;
    }
}
