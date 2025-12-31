package com.dashboard.nutrition.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food_category")
public class FoodCategory {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "code", nullable = false, unique = true)
    private Integer code;

    @Column(name = "description")
    private String description;

    public FoodCategory() {
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return this.code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
