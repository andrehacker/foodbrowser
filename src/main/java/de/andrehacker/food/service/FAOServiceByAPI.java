package de.andrehacker.food.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.andrehacker.food.fao.FAOJsonHelper;

/*
 * Implementation of FAOService using the FAO REST API. Should 
 * work like a proxy, to request data from FAO API and forward 
 * in a custom, more simple, format.
 * 
 * The FAO API is hardly documented, so this is implementation 
 * is currently not working. 
 * 
 * @Service is a specialization of @Component. It enables the 
 * class to be detected during automatic classpath scanning.
 */
@Service("faoServiceByAPI")
public class FAOServiceByAPI implements FAOService {
  
  @Autowired
  private RestTemplate restTemplate;
  
  @Override
  public String getItems() {
    
//    final String faoUrl = "http://data.fao.org/developers/api/v1/en/resources/faostat/crop-prod/item/members.json?page=1&pageSize=10&fields=label@en as product_name,properties.*&sort=label@en";

    // More explicit way for more control:
//    HttpHeaders requestHeaders = new HttpHeaders();
//    requestHeaders.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//    requestHeaders.add("Accept-Encoding","gzip, deflate");
    //  HttpEntity<?> request = new HttpEntity<Object>(requestHeaders);
//  HttpEntity<String> response;
  // Our url is not encoded. Use URI parameter for encoded URLs.
//  response = restTemplate.exchange(faoUrl, HttpMethod.GET, request, String.class);
//    String countries = response.getBody();
//    HttpHeaders responseHeaders = response.getHeaders();
//    System.out.println("Request Header: " + requestHeaders.toString());
//    System.out.println("Response Header: " + responseHeaders.toString());
    
    // http://data.fao.org/developers/api/v1/en/resources/members.json?page=1&pageSize=2&filter=properties.iso3%20gte%20AAA&fields=mnemonic%2Clabel%40en%2Cproperties.*
    // http://data.fao.org/developers/api/v1/en/resources/faostat/crop-prod/item/members.json?page=1&pageSize=10&fields=mnemonic%2Clabel%40en%2Cproperties.label&sort=label%40en
//    String countries = restTemplate.getForObject(faoUrl, String.class);
//    String result = FAOJsonHelper.convertJSON(countries);
    return null;
  }
  
  @Override
  public String getMeasures() {
    // http://aruld.info/resttemplate-the-spring-way-of-accessing-restful-services/
//    String faoUrl = "http://data.fao.org/developers/api/v1/en/resources/faostat/crop-prod/measures.json?page=1&pageSize=50&fields=mnemonic AS type_id,label@en AS &sort=label@en";
//    String types = restTemplate.getForObject(faoUrl, String.class);
//    String result = FAOJsonHelper.convertJSON(types);
    return null;
  }

  @Override
  public String getYears() {
    return null;
  }

  @Override
  public String getCountries() {
    return null;
  }

  /**
   * TODO: Assumes iso3, but receives country_id
   */
  @Override
  public String getPerCountry(int countryID, int measureID, int year) {
    final String faoUrl = "http://data.fao.org/developers/api/v1/en/resources/faostat/crop-prod/facts.json?" +
        "page=1&pageSize=100&" +
        "filter=cnt.iso3 eq {country-iso3} and year eq {year}&" +
        "fields=item AS name, m{type} as value";  // cnt.iso3 AS iso3, year
    System.out.println("Request url: " + faoUrl);
    String json = restTemplate.getForObject(faoUrl, String.class, countryID, year, measureID);
    return FAOJsonHelper.convertJSON(json);
  }

  @Override
  public String getPerItem(int itemID, int measureID, int year) {
    return null;
  }

  @Override
  public String getPerCountryAndTime(int countryID, int measureID,
      List<Integer> items, int yearFrom, int yearTo) {
    return null;
  }
  
//  @Override
//  public List<Product> getProductsDummy() {
//    return new ArrayList<Product>(Arrays.asList(
//        new Product(667, "Tea"),
//        new Product(656, "Coffee")));
//  }

//  @Override
//  public String getProductsJersey() {
//    ClientConfig config = new DefaultClientConfig();
//    Client client = Client.create(config);
//    WebResource service = client.resource(faoUrl);
//    ObjectNode node = service.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ObjectNode.class);
//    System.out.println(node);
//    return node.asText();
//    return "";
//  }

}
