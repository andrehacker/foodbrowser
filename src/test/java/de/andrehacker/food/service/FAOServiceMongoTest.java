package de.andrehacker.food.service;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.andrehacker.food.springconfig.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class FAOServiceMongoTest {

  /*
   * I must use DI to get an instance of this. Creating it manually causes that
   * the IoC does NOT create the AutoWired instances used within the service
   * (e.g. RestTemplate)s
   */
  @Autowired
  FAOServiceMongo service;

  @Test
  public void testItems() {
    String json = service.getItems();
    System.out.println("Items: " + json);
  }

  @Test
  public void testTypes() {
    String json = service.getMeasures();
    System.out.println("Measures: " + json);
  }

  @Test
  public void testCountries() {
    String json = service.getCountries();
    System.out.println("Countries: " + json);
  }

  @Test
  public void testYears() {
    String json = service.getYears();
    System.out.println("Years: " + json);
  }

  @Test
  public void testPerItem() {
    // TODO Test if sorted
    String json = service.getPerItem(667, 5510, 2011);
    System.out.println("PerItem: " + json);
  }

  @Test
  public void testPerCountry() {
    String json = service.getPerCountry(351, 5510, 2011);
    System.out.println("PerCountry: " + json);
  }

  @Test
  public void testPerCountryAndTime() {
    String json = service.getPerCountryAndTime(351, 5510, Arrays.asList(new Integer[]{667, 656}), 2010, 2011);
    System.out.println("PerCountryAndTime: " + json);
  }
  
}
