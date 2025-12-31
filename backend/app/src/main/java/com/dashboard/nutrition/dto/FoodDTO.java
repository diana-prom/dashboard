package com.dashboard.nutrition.dto;

import java.math.BigDecimal;

public class FoodDTO {

    private Integer fdcId;
    private String description;
    private BigDecimal proteinValue;
    private BigDecimal fatValue;
    private BigDecimal carbohydrateValue;
    private Integer foodCategoryId;
    private String category;

    public FoodDTO() {
    }

    public FoodDTO(Integer fdcId, String description, BigDecimal proteinValue, BigDecimal fatValue, BigDecimal carbohydrateValue, Integer foodCategoryId, String category) {
        this.fdcId = fdcId;
        this.description = description;
        this.proteinValue = proteinValue;
        this.fatValue = fatValue;
        this.carbohydrateValue = carbohydrateValue;
        this.foodCategoryId = foodCategoryId;
        this.category = category;
    }


    public Integer getFdcId() { return fdcId; }
    public void setFdcId(Integer fdcId) { this.fdcId = fdcId; }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getProteinValue() {
        return proteinValue;
    }
    public void setProteinValue(BigDecimal proteinValue) {
        this.proteinValue = proteinValue;
    }

    public BigDecimal getFatValue() {
        return fatValue;
    }
    public void setFatValue(BigDecimal fatValue) {
        this.fatValue = fatValue;
    }

    public BigDecimal getCarbohydrateValue() {
        return carbohydrateValue;
    }
    public void setCarbohydrateValue(BigDecimal carbohydrateValue) {
        this.carbohydrateValue = carbohydrateValue;
    }

    public Integer getFoodCategoryId() {return foodCategoryId;}
    public void setFoodCategoryID(Integer foodCategoryId) {this.foodCategoryId = foodCategoryId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
