package com.dashboard.nutrition.dto;

public class FoodDTO {
    private String description;
    private Double proteinValue;
    private Double fatValue;
    private Double carbohydrateValue;

    public FoodDTO() {
    }

    public FoodDTO(String description, Double proteinValue, Double fatValue, Double carbohydrateValue) {
        this.description = description;
        this.proteinValue = proteinValue;
        this.fatValue = fatValue;
        this.carbohydrateValue = carbohydrateValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
