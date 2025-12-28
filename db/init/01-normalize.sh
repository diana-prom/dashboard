#!/bin/bash 
set -euo pipefail

CSV_DIR="/var/lib/mysql-files"
shopt -s nullglob

for f in "$CSV_DIR"/*.csv; do
    echo "[INFO] Normalizing $(basename "$f")"

    # Remove UTF-8 BOM if present
    if head -c 3 "$f" | grep -q $'\xEF\xBB\xBF'; then
        tail -c +4 "$f" > "$f.tmp" && mv "$f.tmp" "$f"
    fi

    # Remove Windows carriage returns
    sed -i '' 's/\r//g' "$f"

    # Wrap fields containing commas in quotes, leave other commas alone
    awk -F, '{
        for(i=1;i<=NF;i++){
            gsub(/^[ \t]+|[ \t]+$/, "", $i)     # Trim spaces
            if($i ~ /,/ && $i !~ /^".*"$/){     # Wrap in quotes only if not already quoted
                $i="\"" $i "\""
            }
        }
        OFS=","
        print $0
    }' "$f" > "$f.tmp" && mv "$f.tmp" "$f"

done
shopt -u nullglob

echo "[INIT] CSV normalization finished."
