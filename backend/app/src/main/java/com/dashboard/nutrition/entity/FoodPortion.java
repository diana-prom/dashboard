package com.dashboard.nutrition.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "food_portion")
public class FoodPortion {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "fdc_id", nullable = false)
    private Integer fdcId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "gram_weight")
    private BigDecimal gramWeight;
    @Column(name = "measure_unit_id")
    private Integer measureUnitId;

    public FoodPortion() {}

    public Integer getFdcId() { return fdcId;}
    public void setFdcId(Integer fdcId) { this.fdcId = fdcId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getGramWeight() { return gramWeight; }
    public void setGramWeight(BigDecimal gramWeight) { this.gramWeight = gramWeight; }

    public Integer getMeasureUnitId() { return measureUnitId; }
    public void setMeasureUnitId(Integer measureUnitId) { this.measureUnitId = measureUnitId; }

}
