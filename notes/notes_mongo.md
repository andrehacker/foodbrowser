

## Export and Import (Postgres => MongoDB)
COPY countries TO '/home/andre/dev/csv-export/countries.csv'  DELIMITER ',' CSV HEADER;
COPY elements TO '/home/andre/dev/csv-export/measures.csv'  DELIMITER ',' CSV HEADER;
COPY items TO '/home/andre/dev/csv-export/items.csv'  DELIMITER ',' CSV HEADER;
COPY production TO '/home/andre/dev/csv-export/facts.csv'  DELIMITER ',' CSV HEADER;

mongoimport -d foodbrowser -c countries -type csv --headerline --drop /home/andre/dev/csv-export/countries.csv
mongoimport -d foodbrowser -c measures -type csv --headerline --drop /home/andre/dev/csv-export/measures.csv
mongoimport -d foodbrowser -c items -type csv --headerline --drop /home/andre/dev/csv-export/items.csv
mongoimport -d foodbrowser -c facts -type csv --headerline --drop /home/andre/dev/csv-export/facts.csv

## MongoDB Export and Import
mongoexport -d food -c countries -o /home/andre/dev/csv-export/countries-export.json
mongoexport -d food -c measures -o /home/andre/dev/csv-export/measures-export.json
mongoexport -d food -c items -o /home/andre/dev/csv-export/items-export.json
mongoexport -d food -c facts -o /home/andre/dev/csv-export/facts-export.json

__Dump__
mongodump -d food -c facts -o /home/andre/dev/csv-export/facts-dump

__Subset__
db.facts.find({measure_id:5510}).forEach(function(doc){
   db.facts_small.insert(doc);
});
mongoexport -d food -c facts_small -o /home/andre/dev/csv-export/facts-small-export.json

## MongoDB config
* need --authenticationDatabase admin (if not added user)
db.addUser( { user: "admin", pwd: "3CBVH8Q_6mE7", roles: [ "readWrite" ] } )

db.runCommand( { compact : 'facts' } )

## MongoDB Admin and Data Import
Attiontion: mongodb/data/journal dir takes up all the space!
Deactivate journaling: Add nojournal = true to mongo config file and delete files in journal folder.
gear restart --cart mongodb-2.2

mongoimport --authenticationDatabase admin -h 127.10.126.130 -u admin -p 3CBVH8Q_6mE7 -d foodbrowser -c countries --drop countries-export.json
mongoimport --authenticationDatabase admin -h 127.10.126.130 -u admin -p 3CBVH8Q_6mE7 -d foodbrowser -c measures --drop measures-export.json
mongoimport --authenticationDatabase admin -h 127.10.126.130 -u admin -p 3CBVH8Q_6mE7 -d foodbrowser -c items --drop items-export.json
mongoimport --authenticationDatabase admin -h 127.10.126.130 -u admin -p 3CBVH8Q_6mE7 -d foodbrowser -c facts facts-small-export.json

db.facts.ensureIndex({item_id: 1})
db.facts.ensureIndex({country_id: 1})
db.facts.ensureIndex({measure_id: 1})
