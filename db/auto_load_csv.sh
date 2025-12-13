#!/bin/bash
set -Eeuo pipefail

# ---------- Configuration ----------
CONTAINER_NAME="mysql80"
DB_NAME="Nutrition_Facts"
MYSQL_USER="root"
MYSQL_PASSWORD="${MYSQL_ROOT_PASSWORD}"
DOCKER_CSV_DIR="/var/lib/mysql-files"
NORMALIZE_SCRIPT="/root/normalize_csvs.sh"
SQL_PROC="/tmp/create_tables.sql"
# ----------------------------------

 handle_error() {
    local exit_code=$?
    echo "[ERROR] Script failed with exit code $exit_code"
    exit $exit_code
}

trap handle_error ERR

echo "[INFO] Creating database if it doesn't exist..."
docker exec -i $CONTAINER_NAME mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -e "CREATE DATABASE IF NOT EXISTS $DB_NAME;"

echo "[INFO] Copying SQL procedure into container..."
docker cp db/CREATE_TABLES.sql "$CONTAINER_NAME":"$SQL_PROC"

echo "[INFO] Creating stored procedure..."
docker exec -i "$CONTAINER_NAME" mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$DB_NAME" < db/CREATE_TABLES.sql

echo "[INFO] Calling CREATE_TABLES procedure..."
docker exec -i "$CONTAINER_NAME" mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -e "USE $DB_NAME; CALL CREATE_TABLES();"

echo "[INFO] Copying normalization script into container.."
docker cp db/normalize_csvs.sh "$CONTAINER_NAME":"$NORMALIZE_SCRIPT"

echo "[INFO] Making normalization script executable..."
docker exec -i "$CONTAINER_NAME" chmod +x "$NORMALIZE_SCRIPT"

echo "[INFO] Normalizing CSVs inside container..."
docker exec -i "$CONTAINER_NAME" "$NORMALIZE_SCRIPT"

echo "[INFO] Loading CSVs into parent tables: food_category, nutrient, food..."
docker exec -i "$CONTAINER_NAME" mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$DB_NAME"<<EOSQL


-- Load food_category first
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_category.csv'
INTO TABLE food_category
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, code, description);

-- Load nutrient 
LOAD DATA INFILE '$DOCKER_CSV_DIR/nutrient.csv'
INTO TABLE nutrient
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@n1,@n2,@n3,@n4,@n5)
SET
    id = @n1,
    name = NULLIF(@n2,''),
    unit_name = NULLIF(@n3,''),
    nutrient_nbr = NULLIF(@n4,''),
    rank_order = NULLIF(@n5,'');

-- Load food 
LOAD DATA INFILE '$DOCKER_CSV_DIR/food.csv'
INTO TABLE food
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(fdc_id, data_type, description, @food_category_id, publication_date)
SET 
    food_category_id = NULLIF(TRIM(@food_category_id), '');

-- Load food_nutrient 
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_nutrient.csv'
INTO TABLE food_nutrient
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@id, @fdc_id, @nutrient_id, @amount)
SET
    id = @id,
    fdc_id = @fdc_id,
    nutrient_id = @nutrient_id,
    amount = NULLIF(TRIM(@amount), '');

-- Loading food_nutrient_conversion_factor
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_nutrient_conversion_factor.csv'
INTO TABLE food_nutrient_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@id, @fdc_id)
SET
    id = @id,
    fdc_id = NULLIF(@fdc_id, '');

-- Load food_calorie_conversion_factor 
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_calorie_conversion_factor.csv'
INTO TABLE food_calorie_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(food_nutrient_conversion_factor_id, protein_value, fat_value, carbohydrate_value);
-- [INFO] food_nutrient.csv loaded successfully!"

LOAD DATA INFILE '$DOCKER_CSV_DIR/food_protein_conversion_factor.csv'
INTO TABLE food_protein_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(food_protein_conversion_factor_id, value);

/*
-- Load CSV into staging table
LOAD DATA INFILE '$DOCKER_CSV_DIR/food_nutrient_conversion_factor.csv'
INTO TABLE staging_food_nutrient_conversion_factor
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@id, @fdc_id)
SET
    id = @id,
    fdc_id = NULLIF(@fdc_id, '');
*/

EOSQL

echo "[INFO] All tables loaded successfully!"