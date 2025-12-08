#!/bin/bash
set -Eeuo pipefail

# ---------- Configuration ----------
CONTAINER_NAME="mysql8"
DB_NAME="Nutrition_Facts"
MYSQL_USER="root"
#MYSQL_PASSWORD=PASSWORD
DOCKER_CSV_DIR="/var/lib/mysql-files"
# ----------------------------------

 handle_error() {
    local exit_code=$?
    echo "An error occurred in the script (exit code: $exit_code)"
    exit $exit_code
}

trap handle_error ERR

echo "Calling stored procedure to create tables..."
docker exec -it $CONTAINER_NAME mysql -u $MYSQL_USER -p -e "USE $DB_NAME; CALL CREATE_TABLES();"

echo "Cleaning CSV line endings inside Docker..."
docker exec -i $CONTAINER_NAME bash -c "for f in $DOCKER_CSV_DIR/*.csv; do sed -i 's/\r\$//' \$f; done"

echo "Loading CSVs into tables..."
docker exec -it $CONTAINER_NAME mysql -u $MYSQL_USER -p -e "
USE $DB_NAME;

-- Load FOOD table
LOAD DATA INFILE '$DOCKER_CSV_DIR/food.csv'
INTO TABLE food
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2,@n3,@n4,@n5,@n6,@n7,@n8,@n9,@n10,@n11,@n12,@n13,@n14)
SET
    fdc_id = @n1,
    data_type = NULLIF(@n2, ''),
    description = NULLIF(@n3, ''),
    food_category_id = NULLIF(@n4, '');

-- Load NUTRIENT table
LOAD DATA INFILE '$DOCKER_CSV_DIR/nutrient.csv'
INTO TABLE nutrient
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2,@n3,@n4,@n5)
SET
    id = @n1,
    name = NULLIF(@n2, ''),
    unit_name = NULLIF(@n3, ''),
    nutrient_nbr = NULLIF(@n4, ''),
    rank_order = NULLIF(@n5, '');

 -- Load FOOD_NUTRIENT table
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_nutrient.csv'
INTO TABLE food_nutrient
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2,@n3,@n4,@n5,@n6,@n7,@n8,@n9,@n10,@n11)
SET
    id = @n1,
    fdc_id = @n2,
    nutrient_id = @n3,
    amount = NULLIF(@n4, '');

-- Load FOOD_CATEGORY table
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_category.csv'
INTO TABLE food_category
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2,@n3)
SET
    category_id = @n1,
    code_number = @n2,
    category_description = NULLIF(@n3, '');

-- Load FOOD_NUTRIENT_CONVERSION_FACTOR
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_nutrient_conversion_factor.csv'
INTO TABLE food_nutrient_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2)
SET
    id = @n1,
    fdc_id = @n2;

-- Load FOOD_CALORIE_CONVERSION_FACTOR table
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_calorie_conversion_factor.csv'
INTO TABLE food_calorie_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2,@n3,@n4)
SET
    food_nutrient_conversion_factor_id = @n1,
    protein_value = NULLIF(@n2, ''),
    fat_value = NULLIF(@n3, ''),
    carbohydrate_value = NULLIF(@n4, '');
    
-- Load FOOD_PROTEIN_CONVERSTION_FACTOR
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_protein_conversion_factor.csv'
INTO TABLE food_protein_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '\"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2)
SET
    food_protein_conversion_factor_ID = @n1,
    value = NULLIF(@n2, '');

"
echo "DONE! All tables are created and CSVs are loaded."