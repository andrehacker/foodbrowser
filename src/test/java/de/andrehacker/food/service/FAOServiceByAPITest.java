package de.andrehacker.food.service;

import org.junit.Ignore;
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
public class FAOServiceByAPITest {

  /*
   * I must use DI to get an instance of this. Creating it manually causes that
   * the IoC does NOT create the AutoWired instances used within the service
   * (e.g. RestTemplate)
   */
  @Autowired
  FAOServiceByAPI service;

  @Ignore
  @Test
  public void testItems() {
    String json = service.getItems();
    System.out.println("Result: " + json);
  }

  @Ignore
  @Test
  public void testTypes() {
    String json = service.getMeasures();
    System.out.println("Result: " + json);
  }

  @Ignore
  @Test
  public void testCountries() {
    String json = service.getCountries();
    System.out.println("Result: " + json);
  }

  @Ignore
  @Test
  public void testYears() {
    String json = service.getYears();
    System.out.println("Result: " + json);
  }

  @Ignore
  @Test
  public void testPerCountry() {
    String json = service.getPerCountry(351, 5510, 2011);
    System.out.println("Result: " + json);
  }

  
}
