package de.andrehacker.food.fao;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FAOJsonHelper {
  
  /**
   * Convert from fao format to proprietary (more simple) format
   */
  public static String convertJSON(String faoJson) {
    String converted = "";
    
    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode;
    try {
      rootNode = mapper.readTree(faoJson);
      JsonNode items = rootNode.path("result").path("list").path("items");
      converted = items.toString();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return converted;
  }


//  public static String makePrettyJson(final String json) {
//    ObjectMapper mapper = new ObjectMapper();
//    String pretty = "";
//    try {
//      pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//    } catch (JsonProcessingException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    return pretty;
//  }
  
}
