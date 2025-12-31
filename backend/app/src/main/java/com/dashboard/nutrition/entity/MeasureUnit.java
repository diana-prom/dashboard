package com.dashboard.nutrition.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "measure_unit")
public class MeasureUnit {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;

    public MeasureUnit() {}

    public Integer getId(){ return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


}

