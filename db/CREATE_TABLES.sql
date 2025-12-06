USE Nutrition_Facts;

DELIMITER //

CREATE PROCEDURE CREATE_TABLES()
BEGIN
    -- FOOD table
    DROP TABLE IF EXISTS food;
    CREATE TABLE food (
        fdc_id VARCHAR(20),
        data_type VARCHAR(200),
        description VARCHAR(500),
        food_category_id VARCHAR(100)
    );

    -- NUTRIENT table
    DROP TABLE IF EXISTS nutrient;
    CREATE TABLE nutrient (
        id INT,
        name VARCHAR(200),
        unit_name VARCHAR(100),
        nutrient_nbr INT,
        rank_order INT
    );

    -- FOOD NUTRIENT table
    DROP TABLE IF EXISTS food_nutrient;
    CREATE TABLE food_nutrient (
        id INT,
        fdc_id VARCHAR (20),
        nutrient_id INT, 
        amount DECIMAL(10, 3)
    );

    -- FOOD_CATEGORY table
    DROP TABLE IF EXISTS food_category;
    CREATE TABLE food_category (
        category_id INT,
        code_number INT,
        category_description VARCHAR(200)
    );

    -- FOOD_NUTRIENT_CONVERSION_FACTOR
    DROP TABLE IF EXISTS food_nutrient_conversion_factor;
    CREATE TABLE food_nutrient_conversion_factor (
        id INT,
        fdc_id VARCHAR(20)
    );
    
    -- FOOD_CALORIE_CONVERSION_FACTOR 
    DROP TABLE IF EXISTS food_calorie_conversion_factor;
    CREATE TABLE food_calorie_conversion_factor (
        food_nutrient_conversion_factor_id INT,
        protein_value DECIMAL(10, 2),
        fat_value DECIMAL(10, 2),
        carbohydrate_value DECIMAL(10, 2)
    );

    -- FOOD_PROTEIN_CONVERSTION_FACTOR
    DROP TABLE IF EXISTS food_protein_conversion_factor;
    CREATE TABLE food_protein_conversion_factor (
        food_protein_conversion_factor_id INT,
        value DECIMAL(10, 2)
    );
END //

DELIMITER ;
