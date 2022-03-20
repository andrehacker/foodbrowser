#!/bin/bash


set -e

create_container() {
  docker run --name $CONTAINER_NAME -e POSTGRES_USER=$POSTGRES_USER -e POSTGRES_PASSWORD=$POSTGRES_PWD -d postgres
}

download_files() {
  #wget https://fenixservices.fao.org/faostat/static/bulkdownloads/Population_E_All_Data_\(Normalized\).zip
  #unzip Population_E_All_Data_\(Normalized\).zip
  wget https://fenixservices.fao.org/faostat/static/bulkdownloads/Production_Crops_Livestock_E_All_Data_\(Normalized\).zip
  unzip Production_Crops_Livestock_E_All_Data_\(Normalized\).zip
}

run_sql() {
  sqlfile=$1
  docker cp $sqlfile $CONTAINER_NAME:/tmp/
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -d $DB_NAME -f /tmp/$sqlfile -a -v ON_ERROR_STOP=1
}

import_file() {
  sourcefile=$1
  targettable=$2
  docker cp $sourcefile $CONTAINER_NAME:/tmp/
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -d $DB_NAME -c "COPY $targettable FROM '/tmp/$sourcefile' WITH ENCODING 'latin1' DELIMITER ',' ESCAPE '\"' QUOTE '\"' CSV HEADER;"
}

create_db() {
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "DROP DATABASE IF EXISTS $DB_NAME"
  docker exec -it $CONTAINER_NAME psql -U $POSTGRES_USER -c "CREATE DATABASE $DB_NAME"
}

load_and_transform() {
  run_sql "faostat-ddl.sql"
  import_file "countries.csv" "countries"

  import_file "Production_Crops_Livestock_E_All_Data_(Normalized).csv" "production_raw"
  #import_file "Population_E_All_Data_(Normalized).csv" "production_raw"
  
  run_sql "transform-faostat.sql"

  run_sql "faostat-views.sql"
}

CONTAINER_NAME="postgres-fao"
POSTGRES_USER="postgres" # this is also default superuser
POSTGRES_PWD="postgres"
DB_NAME="faostat"

create_container
download_files
create_db
load_and_transform

