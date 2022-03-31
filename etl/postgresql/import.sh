#!/bin/bash

set -e

create_container() {
  docker run --name $CONTAINER_NAME -p 5432:5432 -e POSTGRES_USER=$POSTGRES_USER -e POSTGRES_PASSWORD=$POSTGRES_PWD -d postgres
}

download_files() {
  wget -nc https://fenixservices.fao.org/faostat/static/bulkdownloads/Population_E_All_Data_\(Normalized\).zip
  unzip -n Population_E_All_Data_\(Normalized\).zip
  wget -nc https://fenixservices.fao.org/faostat/static/bulkdownloads/Production_Crops_Livestock_E_All_Data_\(Normalized\).zip
  unzip -n Production_Crops_Livestock_E_All_Data_\(Normalized\).zip
  wget -nc https://fenixservices.fao.org/faostat/static/bulkdownloads/Trade_CropsLivestock_E_All_Data_\(Normalized\).zip
  unzip -n Trade_CropsLivestock_E_All_Data_\(Normalized\).zip
}

run_sql() {
  sqlfile=$1
  docker cp $sqlfile $CONTAINER_NAME:/tmp/
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -d $DB_NAME -f /tmp/$sqlfile -a -v ON_ERROR_STOP=1
}

import_file() {
  sourcefile=$1
  targettable=$2
  columns=$3 # optional
  docker cp $sourcefile $CONTAINER_NAME:/tmp/
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -d $DB_NAME -c "COPY $targettable $columns FROM '/tmp/$sourcefile' WITH ENCODING 'latin1' DELIMITER ',' CSV HEADER QUOTE '\"' ESCAPE '\"';"
}

create_db() {
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "DROP DATABASE IF EXISTS $DB_NAME"
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "CREATE DATABASE $DB_NAME"
}

load_and_transform() {
  run_sql "faostat-ddl.sql"
  import_file "faostat-definitions-countries.csv" "countries_raw"
  import_file "faostat-definitions-country-groups.csv" "country_groups_raw"
  import_file "Production_Crops_Livestock_E_All_Data_(Normalized).csv" "production_raw" "(country_code,country,item_code,item,element_code,element,year_code,year,unit,value,flag)" # csv has not all cols
  import_file "Population_E_All_Data_(Normalized).csv" "production_raw"
  import_file "Trade_CropsLivestock_E_All_Data_(Normalized).csv" "production_raw" "(country_code,country,item_code,item,element_code,element,year_code,year,unit,value,flag)" 
  
  run_sql "transform-faostat.sql"

  run_sql "faostat-views.sql"
}

CONTAINER_NAME="postgres-fao"
POSTGRES_USER="postgres"
POSTGRES_PWD="postgres"
DB_NAME="faostat"

# create_container
download_files
create_db
load_and_transform

