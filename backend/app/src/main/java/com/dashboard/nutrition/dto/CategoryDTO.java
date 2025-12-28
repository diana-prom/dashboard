package com.dashboard.nutrition.dto;

public class CategoryDTO {
    private Integer id;
    private Integer code;
    private String description;

    public CategoryDTO(Integer id, Integer code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer categoryId) {
        this.id = categoryId;
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
