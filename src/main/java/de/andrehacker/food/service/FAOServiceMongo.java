package de.andrehacker.food.service;

import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;


/**
 * Implementation of FAOService using the mongodb database with the driver of
 * mongodb (hibernate and/or spring-data implementations might follow).
 * 
 * This class has to be instantiated manually as a bean, because there is no
 * default constructor.
 */
//@Service
public class FAOServiceMongo implements FAOService {
  
  private final DB db;
  
  /**
   * Use constructor-based injection. Allows us to inject specific instances of
   * the client, e.g. for testing we could connect to a test db, or we inject a
   * mock. We can't, however, inject our own implementation, because MongoClient
   * is not an interface.
   */
  public FAOServiceMongo(DB db) {
    this.db = db;
  }

  @Override
  public String getItems() {
    DBCollection items = db.getCollection("items");
    DBCursor cursor = items.find();
    return JSON.serialize(cursor);
  }

  @Override
  public String getMeasures() {
    return JSON.serialize(db.getCollection("measures").find());
  }

  @Override
  public String getYears() {
    return FAOServiceConstants.years;
  }

  @Override
  public String getCountries() {
    return JSON.serialize(db.getCollection("countries").find());
  }

  @Override
  public String getPerItem(int itemID, int measureID, int year) {
    BasicDBObject query = new BasicDBObject();
    query.put("item_id", itemID);
    query.put("measure_id", measureID);
    query.put("year", year);
    query.put("country_id", new BasicDBObject("$lt", 5000));
    DBCursor result = db.getCollection("facts").find(query).sort(new BasicDBObject("value", -1));
    return JSON.serialize(result);
  }

  @Override
  public String getPerCountry(int countryID, int measureID, int year) {
    BasicDBObject query = new BasicDBObject();
    query.put("country_id", countryID);
    query.put("measure_id", measureID);
    query.put("year", year);
    // exclude product groups
    query.put("item_id", new BasicDBObject("$lt", 1000));
    DBCursor result = db.getCollection("facts").find(query).sort(new BasicDBObject("value", -1));
    return JSON.serialize(result);
  }

  @Override
  public String getPerCountryAndTime(int countryID, int measureID,
      List<Integer> items, int yearFrom, int yearTo) {
    // Match
    BasicDBObject matchQuery = new BasicDBObject();
    matchQuery.put("country_id", countryID);
    matchQuery.put("measure_id", measureID);
    BasicDBObject yearFilter = new BasicDBObject();
    yearFilter.append("$gte", yearFrom);
    yearFilter.append("$lte", yearTo);
    matchQuery.put("year", yearFilter);
    // exclude product groups
    matchQuery.put("item_id", 100);
    //System.out.println(items.toString());
    matchQuery.put("item_id", new BasicDBObject("$in", items));
    
    // Group
    // We can also write this complex query as JSON String and use
    // JSON.parse() to obtain the DBObject representation.
    String groupQueryStr = "{ " + 
        "_id : {country_id: '$country_id', item_id: '$item_id'}," +
        "fact_count: { $sum : 1 }," +
        "values: {$push: {year: '$year', value: '$value', flag: '$flag'}} }";
    Object groupQuery = JSON.parse(groupQueryStr);
    
    // Project
    String projectQueryStr = "{" + 
        "_id : 0," + 
        "item_id: '$_id.item_id'," +
        "values: '$values'}";
    Object projectQuery = JSON.parse(projectQueryStr);
    
    AggregationOutput output = db.getCollection("facts").aggregate(
        new BasicDBObject("$match", matchQuery),
        new BasicDBObject("$group", groupQuery),
        new BasicDBObject("$project", projectQuery));
    
    return output.getCommandResult().get("result").toString();
  }

}
