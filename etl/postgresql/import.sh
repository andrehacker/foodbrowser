#!/bin/bash

set -e

create_container() {
  docker run --name $CONTAINER_NAME -p 5432:5432 -e POSTGRES_USER=$POSTGRES_USER -e POSTGRES_PASSWORD=$POSTGRES_PWD -d postgres
  # wait until ready
  RETRIES=10
  until docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "select 1" > /dev/null 2>&1 || [ $RETRIES -eq 0 ]; do
    echo "Waiting for postgres server, $((RETRIES--)) remaining attempts..."
    sleep 1
  done
}

create_db() {
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "DROP DATABASE IF EXISTS $DB_NAME"
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "CREATE DATABASE $DB_NAME"
}

download_file() {
  file_url=$1
  file_name=$(basename $file_url)
  wget -nc --directory-prefix $TMP_DIR $file_url
  unzip -d $TMP_DIR -n "/tmp/foodbrowser/${file_name}"
}

import_file() {
  sourcefile=$1
  sourcefilename=$(basename $sourcefile)
  targettable=$2
  columns=$3 # optional
  docker cp "$sourcefile" $CONTAINER_NAME:/tmp/
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -d $DB_NAME -c "COPY $targettable $columns FROM '/tmp/$sourcefilename' WITH ENCODING 'latin1' DELIMITER ',' CSV HEADER QUOTE '\"' ESCAPE '\"';"
}

run_sql() {
  sqlfile=$1
  docker cp $sqlfile $CONTAINER_NAME:/tmp/
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -d $DB_NAME -f /tmp/$sqlfile -a -v ON_ERROR_STOP=1
}

load_and_transform() {
  run_sql "faostat-ddl.sql"
  import_file "faostat-definitions-countries.csv" "countries_raw"
  import_file "faostat-definitions-country-groups.csv" "country_groups_raw"
  import_file "faostat-definitions-flags.csv" "flags"
  import_file "faostat-definitions-abbreviations.csv" "abbreviations"
  import_file "faostat-definitions-units.csv" "units"
  import_file "faostat-definitions-items.csv" "items_raw"
  
  download_file https://fenixservices.fao.org/faostat/static/bulkdownloads/Production_Crops_Livestock_E_All_Data_\(Normalized\).zip
  import_file "${TMP_DIR}Production_Crops_Livestock_E_All_Data_(Normalized).csv" "production_raw" "(country_code,country,item_code,item,element_code,element,year_code,year,unit,value,flag)" # csv has not all cols

  download_file https://fenixservices.fao.org/faostat/static/bulkdownloads/Population_E_All_Data_\(Normalized\).zip
  import_file "${TMP_DIR}Population_E_All_Data_(Normalized).csv" "production_raw"
  
  download_file https://fenixservices.fao.org/faostat/static/bulkdownloads/Trade_CropsLivestock_E_All_Data_\(Normalized\).zip
  import_file "${TMP_DIR}Trade_CropsLivestock_E_All_Data_(Normalized).csv" "production_raw" "(country_code,country,item_code,item,element_code,element,year_code,year,unit,value,flag)" 

  #download_file https://fenixservices.fao.org/faostat/static/bulkdownloads/Trade_DetailedTradeMatrix_E_All_Data_\(Normalized\).zip
  #import_file "${TMP_DIR}Trade_DetailedTradeMatrix_E_All_Data_(Normalized).csv" "tradematrix_raw"
  
  run_sql "transform-faostat.sql"

  run_sql "faostat-views.sql"
}

CONTAINER_NAME="postgres-fao"
POSTGRES_USER="postgres"
POSTGRES_PWD="postgres"
DB_NAME="faostat"
TMP_DIR="/tmp/foodbrowser/"

create_container
create_db
load_and_transform

