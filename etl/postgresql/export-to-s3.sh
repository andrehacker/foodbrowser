#!/bin/bash

set -e

export_table() {
  table=$1
  docker exec postgres-fao psql -U postgres -d faostat -c "COPY $table TO '/tmp/export/$table.csv' DELIMITER ',';"
  docker exec postgres-fao tar czf /tmp/export/$table.tar.gz -C /tmp/export $table.csv
}


CONTAINER_NAME="postgres-fao"

docker exec -u postgres postgres-fao mkdir /tmp/export

export_table abbreviations
export_table countries
export_table elements
export_table flags
export_table items
export_table production
export_table tradematrix
export_table units

docker exec postgres-fao bash -c 'rm /tmp/export/*.csv'
docker cp $CONTAINER_NAME:/tmp/export /tmp/

