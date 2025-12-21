package com.dashboard.nutrition.repository;

import com.dashboard.nutrition.entity.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void cleanDb() {
        foodRepository.deleteAll();
    }

    @Test
    @DisplayName("h2DataSource")
    void testH2DataSource() throws Exception {
        String url = dataSource.getConnection().getMetaData().getURL();
        assertNotNull(url);
    }

    @Test
    @DisplayName("save food persists entity and generates ID")
    void testSaveFood_persistEntity() {
        Food food = new Food();
        food.setDescription("Whole Milk");

        food = foodRepository.saveAndFlush(food);
        assertNotNull(food.getFdc_id(), "ID should be generated");
    }

    @Test
    @DisplayName("findFirstByDescription")
    void testFindFirstByDescription() {
        String searchName = "Paint";
        Food food = new Food();
        food.setDescription(searchName);
        foodRepository.saveAndFlush(food);

        Food found = foodRepository.findFirstByDescriptionContainingIgnoreCase(searchName)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        assertNotNull(found.getDescription());
    }

    @Test
    @DisplayName("findFirstByDescriptionContainingIgnoreCase returns matching food")
    void testFindByDescriptionContainingIgnoreCase_returnsMatch() {
        String searchTerm = "Milk";
        Food food = new Food();
        food.setDescription("Whole Milk");
        foodRepository.saveAndFlush(food);

        List<Food> foods = foodRepository.findByDescriptionContainingIgnoreCase(searchTerm);
        assertFalse(foods.isEmpty(), "Expected at least one food with 'Milk'");
    }
}
