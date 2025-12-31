package com.dashboard.nutrition.dto;

import java.math.BigDecimal;

/**
 * DTO for the aggregated food portion endpoint.
 *
 * This DTO does not map 1:1 to a table.
 * It is constructed in the service layer by joining data
 * from food_portion and measure unit tables.
 */
public class FoodPortionDTO {

    private BigDecimal amount;
    private BigDecimal gramWeight;
    private String unitName;

    public FoodPortionDTO(BigDecimal amount, BigDecimal gramWeight, String unitName) {
        this.amount = amount;
        this.gramWeight = gramWeight;
        this.unitName = unitName;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount){ this.amount = amount;}

    public BigDecimal getGramWeight(){return gramWeight; }
    public void setGramWeight(BigDecimal gramWeight){this.gramWeight = gramWeight;}

    public String getUnitName(){ return unitName; }
    public void setUnitName(String unitName){this.unitName = unitName; }
}

