package com.dashboard.nutrition.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @Column(name = "fdc_id")
    private Integer fdc_id;

    @Column(name = "description")
    private String description;

    @JsonManagedReference
    @OneToOne(mappedBy = "food", fetch = FetchType.LAZY)
    private FoodNutrientConversionFactor nutrientConversionFactor;

    public Integer getFdc_id() { return fdc_id;}
    //public void setFdc_id(Integer fdc_id) {this.fdc_id=fdc_id;}

    public String getDescription() { return description;}
    //public void setDescription(String description) { this.description = description;}

    public FoodNutrientConversionFactor getNutrientConversionFactor() {
        return nutrientConversionFactor;
    }
}
