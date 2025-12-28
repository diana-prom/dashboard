#!/bin/bash
set -Eeuo pipefail

# ---------- Configuration ----------
DB_NAME="Nutrition_Facts"
MYSQL_USER="root"
MYSQL_PASSWORD="${MYSQL_ROOT_PASSWORD:-root}"
DOCKER_CSV_DIR="/var/lib/mysql-files"
LOG_FILE="/var/log/mysql/init.log"
mkdir -p "$(dirname "$LOG_FILE")"
exec > >(tee "$LOG_FILE") 2>&1
# ----------------------------------

handle_error() {
    local exit_code=$?
    echo "[ERROR] Script failed with exit code $exit_code at line $LINENO"
    exit $exit_code
}
trap handle_error ERR


# Tables to check/load
tables=("food_nutrient_conversion_factor" "food_protein_conversion_factor" "food_calorie_conversion_factor" \
        "food_nutrient" "food_portion" "food" "measure_unit" "nutrient" "food_category")


# Function to log table existence and row count
log_table_status() {
  for t in "${tables[@]}"; do
    mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$DB_NAME" -N -e "
      SELECT 
        IF(EXISTS(
          SELECT 1 
          FROM INFORMATION_SCHEMA.TABLES 
          WHERE TABLE_SCHEMA='$DB_NAME' 
            AND TABLE_NAME='$t'
        ), 
        CONCAT('Table ', '$t', ' exists with ', (SELECT COUNT(*) FROM $t), ' rows'), 
        CONCAT('Table ', '$t', ' does NOT exist')) AS status;
    "
  done
}

# -------------------------
# Load CSV helper
# -------------------------
load_csv() {
    local table_name=$1
    local csv_file=$2
    local columns=$3
    local set_stmt="${4:-}"

    if [ ! -f "$csv_file" ]; then
        echo "[WARN] CSV file $csv_file not found, skipping $table_name"
        return
    fi

    if ! mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$DB_NAME" -e "USE $DB_NAME; SHOW TABLES LIKE '$table_name';" | grep -q "$table_name"; then
        echo "[WARN] Table $table_name does not exist, skipping."
        return
    fi


    echo "[INFO] Loading $table_name from $(basename "$csv_file")..."

    mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$DB_NAME" <<EOSQL
SET FOREIGN_KEY_CHECKS=0;

LOAD DATA INFILE '$csv_file'
INTO TABLE $table_name
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
ESCAPED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
$columns
$set_stmt;

SELECT CONCAT('WARNINGS for table: ', '$table_name') AS info;
SHOW WARNINGS;

SET FOREIGN_KEY_CHECKS=1;
EOSQL

    echo "[INFO] $table_name loaded. Row count:"
    mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$DB_NAME" -e "SELECT '$table_name' AS table_name, COUNT(*) AS row_count FROM $table_name;"
    echo "-------------------------------"
}

# -------------------------
# Load CSVs in dependency order
# -------------------------
load_csv "food_category" "$DOCKER_CSV_DIR/food_category.csv" "(id, code, description)"
load_csv "nutrient" "$DOCKER_CSV_DIR/nutrient.csv" "(@id, @name, @unit_name, @nutrient_nbr, @rank_order)" \
         "SET id=@id, name=NULLIF(TRIM(@name),''), unit_name=NULLIF(TRIM(@unit_name),''), nutrient_nbr=NULLIF(TRIM(@nutrient_nbr),''), rank_order=NULLIF(TRIM(@rank_order),'')"
load_csv "measure_unit" "$DOCKER_CSV_DIR/measure_unit.csv" "(id, name)"
load_csv "food" "$DOCKER_CSV_DIR/food.csv" \
"(fdc_id, data_type, description, @food_category_id, publication_date)" \
"SET food_category_id = NULLIF(TRIM(@food_category_id), '')"
load_csv "food_portion" "$DOCKER_CSV_DIR/food_portion.csv" "(id, fdc_id, @seq_num, amount, measure_unit_id, @portion_description, @modifier, gram_weight, @data_points, @foodnote, @min_year_acquired)" \
         "SET seq_num = NULLIF(TRIM(@seq_num),''), min_year_acquired = NULLIF(TRIM(@min_year_acquired),''), portion_description = NULLIF(TRIM(@portion_description),''), modifier = NULLIF(TRIM(@modifier),''), foodnote = NULLIF(TRIM(@foodnote),''), data_points = NULLIF(TRIM(@data_points),'')"
load_csv "food_nutrient" "$DOCKER_CSV_DIR/food_nutrient.csv" "(id, fdc_id, nutrient_id, @amount)" \
         "SET amount=NULLIF(TRIM(@amount),'')"
load_csv "food_nutrient_conversion_factor" "$DOCKER_CSV_DIR/food_nutrient_conversion_factor.csv" "(id, @fdc_id)" \
         "SET fdc_id=NULLIF(TRIM(@fdc_id),'')"
load_csv "food_calorie_conversion_factor" "$DOCKER_CSV_DIR/food_calorie_conversion_factor.csv" "(food_nutrient_conversion_factor_id, protein_value, fat_value, carbohydrate_value)"
load_csv "food_protein_conversion_factor" "$DOCKER_CSV_DIR/food_protein_conversion_factor.csv" "(food_protein_conversion_factor_id, value)"


echo "Checking tables..."
log_table_status


echo "[INIT] CSV loading complete."

 