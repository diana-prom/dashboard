package com.dashboard.nutrition.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dashboard.nutrition.entity.MeasureUnit;


@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Integer> {
}
