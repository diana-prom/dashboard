USE Nutrition_Facts;

DROP PROCEDURE IF EXISTS CREATE_TABLES;

DELIMITER //

CREATE PROCEDURE CREATE_TABLES()
BEGIN
  SET FOREIGN_KEY_CHECKS=0;
  
    DROP TABLE IF EXISTS food_nutrient_conversion_factor;
    DROP TABLE IF EXISTS food_protein_conversion_factor;
    DROP TABLE IF EXISTS food_calorie_conversion_factor;
  
    DROP TABLE IF EXISTS food_nutrient;
    DROP TABLE IF EXISTS food_portion;
    DROP TABLE IF EXISTS food;

    DROP TABLE IF EXISTS measure_unit;
    DROP TABLE IF EXISTS nutrient;
    DROP TABLE IF EXISTS food_category;

CREATE TABLE IF NOT EXISTS food_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code INT NOT NULL UNIQUE,
    description VARCHAR(200)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS nutrient (
    id INT PRIMARY KEY,
    name VARCHAR(200),
    unit_name VARCHAR(100),
    nutrient_nbr INT,
    rank_order INT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food (
    fdc_id INT PRIMARY KEY,
    data_type VARCHAR(200),
    description VARCHAR(500),
    food_category_id INT,
    publication_date VARCHAR(100),
    CONSTRAINT fk_food_category
        FOREIGN KEY (food_category_id)
        REFERENCES food_category(id)
        ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS measure_unit (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food_portion (
    id INT PRIMARY KEY,
    fdc_id INT NOT NULL,
    measure_unit_id INT,
    seq_num INT,
    amount DECIMAL(10,3),
    gram_weight DECIMAL(10,3),
    portion_description VARCHAR(200),
    modifier VARCHAR(100),
    data_points INT,
    foodnote VARCHAR(100),
    min_year_acquired INT,
    CONSTRAINT fk_fp_food
        FOREIGN KEY (fdc_id)
        REFERENCES food(fdc_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_fp_unit
        FOREIGN KEY (measure_unit_id)
        REFERENCES measure_unit(id)
        ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food_nutrient (
    id INT PRIMARY KEY,
    fdc_id INT,
    nutrient_id INT,
    amount DECIMAL(10,3),
    CONSTRAINT fk_fn_food
        FOREIGN KEY (fdc_id)
        REFERENCES food(fdc_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_fn_nutrient
        FOREIGN KEY (nutrient_id)
        REFERENCES nutrient(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food_nutrient_conversion_factor (
    id INT PRIMARY KEY,
    fdc_id INT,
    CONSTRAINT fk_fncf_food
        FOREIGN KEY (fdc_id)
        REFERENCES food(fdc_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food_calorie_conversion_factor (
    food_nutrient_conversion_factor_id INT PRIMARY KEY,
    protein_value DECIMAL(10,2),
    fat_value DECIMAL(10,2),
    carbohydrate_value DECIMAL(10,2),
    CONSTRAINT fk_fccf_fncf
        FOREIGN KEY (food_nutrient_conversion_factor_id)
        REFERENCES food_nutrient_conversion_factor(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food_protein_conversion_factor (
    food_protein_conversion_factor_id INT PRIMARY KEY,
    value DECIMAL(10,2)
) ENGINE=InnoDB;
SELECT 'Created table: food_protein_conversion_factor (no foreign key)';
SET FOREIGN_KEY_CHECKS=1;
END//

DELIMITER ;
