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
    private Integer fdc_id;
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodNutrientConversionFactor> nutrientConversionFactors = new ArrayList<>();

    public Integer getFdc_id() {
        return fdc_id;
    }

    public void setFdc_id(Integer fdc_id) {
        this.fdc_id = fdc_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FoodNutrientConversionFactor> getNutrientConversionFactors() {
        return nutrientConversionFactors;
    }

    public void setNutrientConversionFactors(List<FoodNutrientConversionFactor> nutrientConversionFactors) {
        this.nutrientConversionFactors = nutrientConversionFactors;
    }

    public void addNutrientConversionFactor(FoodNutrientConversionFactor nutrient) {
        nutrient.setFood(this);
    }
}
