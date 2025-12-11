USE Nutrition_Facts;

DROP procedure IF EXISTS `CREATE_TABLES`;

DELIMITER //

CREATE PROCEDURE CREATE_TABLES()
BEGIN
  
    DROP TABLE IF EXISTS food_protein_conversion_factor;
    DROP TABLE IF EXISTS food_calorie_conversion_factor;
    DROP TABLE IF EXISTS food_nutrient_conversion_factor;
    DROP TABLE IF EXISTS food_nutrient;
    DROP TABLE IF EXISTS food;


    DROP TABLE IF EXISTS nutrient;
    DROP TABLE IF EXISTS food_category;
    DROP TABLE IF EXISTS staging_food_nutrient;
    DROP TABLE IF EXISTS staging_food_nutrient_conversion_factor;
    DROP TABLE IF EXISTS staging_food_calorie_conversion_factor;

SELECT 'Dropped table: food_category';

CREATE TABLE food_category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code INT NOT NULL UNIQUE,
    description VARCHAR(200)
)  ENGINE=INNODB;
SELECT 'Created table: food_category';


SELECT 'Dropped table: nutrient';

CREATE TABLE nutrient (
    id INT PRIMARY KEY,
    name VARCHAR(200),
    unit_name VARCHAR(100),
    nutrient_nbr INT,
    rank_order INT
)  ENGINE=INNODB;
SELECT 'Created table: nutrient';
   
    
   
SELECT 'Dropped table: food';

CREATE TABLE food (
    fdc_id INT PRIMARY KEY,
    data_type VARCHAR(200),
    description VARCHAR(500),
    food_category_id INT NULL,
    publication_date VARCHAR(100),
    FOREIGN KEY (food_category_id)
        REFERENCES food_category (id)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB;
SELECT 'Created table: food';


 
SELECT 'Dropped table: food_nutrient';

CREATE TABLE food_nutrient (
    id INT PRIMARY KEY,
    fdc_id INT,
    nutrient_id INT,
    amount DECIMAL(10 , 3 ) NULL,
    FOREIGN KEY (fdc_id)
        REFERENCES food (fdc_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (nutrient_id)
        REFERENCES nutrient (id)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB;
SELECT 'Created table: food_nutrient';

 
SELECT 'Dropped table: food_nutrient_conversion_factor';

CREATE TABLE food_nutrient_conversion_factor (
    id INT PRIMARY KEY,
    fdc_id INT NULL,
    FOREIGN KEY (fdc_id)
        REFERENCES food (fdc_id)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB;
SELECT 'Created table: food_nutrient_conversion_factor';


SELECT 'Dropped table: food_calorie_conversion_factor';

CREATE TABLE food_calorie_conversion_factor (
    food_nutrient_conversion_factor_id INT PRIMARY KEY,
    protein_value DECIMAL(10 , 2 ),
    fat_value DECIMAL(10 , 2 ),
    carbohydrate_value DECIMAL(10 , 2 )
)  ENGINE=INNODB;

ALTER TABLE food_calorie_conversion_factor
ADD CONSTRAINT fk_fccf_fncf
FOREIGN KEY (food_nutrient_conversion_factor_id)
REFERENCES food_nutrient_conversion_factor(id)
ON DELETE CASCADE
ON UPDATE CASCADE;
SELECT 'Created table: food_calorie_conversion_factor';


SELECT 'Dropped table: food_protein_conversion_factor';

CREATE TABLE food_protein_conversion_factor (
    food_protein_conversion_factor_id INT PRIMARY KEY,
    value DECIMAL(10 , 2 )
)  ENGINE=INNODB;
SELECT 'Created table: food_protein_conversion_factor (no foreign key)'; 
    
END //

DELIMITER ;
