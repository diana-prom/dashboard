#!/bin/bash
set -euo pipefail

CSV_DIR="/var/lib/mysql-files"

echo "[INFO] Normalizing CSVs in $CSV_DIR..."

for f in "$CSV_DIR"/*.csv; do
    echo "[INFO] Processing $(basename "$f")"

    # Remove Windows carriage returns
    sed -i 's/\r//g' "$f"

    # Replace empty numeric fields (,,) with \N
    sed -i -E 's/,,/,\\N,/g; s/,$/,\\N/' "$f"

    # Replace fields that contain only spaces: ", ,"
    sed -i -E 's/,[[:space:]]*,/,\\N,/g' "$f"

    # Wrap fields with commas in double quotes
    awk -F, '{
        for(i=1;i<=NF;i++){
            gsub(/^[ \t]+|[ \t]+$/, "", $i)
            if($i ~ /,/ && $i !~ /^".*"$/){
                $i="\"" $i "\""
            }
        }
        OFS=","
        print $0
    }' "$f" > "$f.tmp" && mv "$f.tmp" "$f"
done

echo "[INIT] CSV normalization finished."
