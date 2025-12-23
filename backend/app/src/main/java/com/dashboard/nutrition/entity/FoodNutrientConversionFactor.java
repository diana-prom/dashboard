package com.dashboard.nutrition.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "food_nutrient_conversion_factor")
public class FoodNutrientConversionFactor {

    @Id
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fdc_id", nullable = false)
    private Food food;

    @OneToOne(mappedBy = "nutrientConversionFactor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private FoodCalorieConversionFactor calorieConversionFactor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;

        if (food != null && !food.getNutrientConversionFactors().contains(this)) {
            food.getNutrientConversionFactors().add(this);
        }
    }

    public FoodCalorieConversionFactor getCalorieConversionFactor() {
        return calorieConversionFactor;
    }

    public void setCalorieConversionFactor(FoodCalorieConversionFactor calorie) {
        this.calorieConversionFactor = calorie;

        if (calorie != null) {
            calorie.setNutrientConversionFactor(this);
        }
    }
}
