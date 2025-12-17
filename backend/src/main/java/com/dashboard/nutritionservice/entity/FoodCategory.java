package com.dashboard.nutritionservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food_category")
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer categoryId;

    @Column(name = "code")
    private Integer code;

    @Column(name = "description")
    private String description;

    // Getters and Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
