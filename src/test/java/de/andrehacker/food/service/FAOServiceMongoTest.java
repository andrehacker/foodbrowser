package de.andrehacker.food.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.andrehacker.food.springconfig.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class })
public class FAOServiceMongoTest {

  /*
   * I must use DI to get an instance of the service. Creating it manually
   * causes that the IoC does NOT create the injected instances used within the
   * service (e.g. RestTemplate)
   * 
   * @Autowired and @Inject can be used interchangeably, autowired is spring
   * specific, inject is Java CDI spec (JSR 330: Dependency Injection for Java).
   */
  @Inject
  FAOServiceMongo service;

  @Inject
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testItems() throws Exception {
    // String json = service.getItems();
    // System.out.println("Items: " + json);
    mockMvc.perform(get("/items").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(100))))
        .andExpect(jsonPath("$[*]._id", hasItem(667)))
        .andExpect(jsonPath("$[*].name", hasItem("Tea")));
  }

  @Test
  public void testMeasures() throws Exception {
    mockMvc.perform(get("/measures").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[*]._id", hasItem(5510)))
        .andExpect(jsonPath("$[*].name", hasItem("Production")))
        .andExpect(jsonPath("$[*].unit", hasItem("tonnes")));
  }

  @Test
  public void testCountries() throws Exception {
    mockMvc.perform(get("/countries").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[*]._id", hasItem(41)))
        .andExpect(jsonPath("$[*].iso2", hasItem("CN")))
        .andExpect(jsonPath("$[*].name", hasItem("China, mainland")));
  }

  @Test
  public void testYears() throws Exception {
    mockMvc.perform(get("/years").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[*]", hasItem(2011)));
  }

  @Test
  public void testPerItem() throws Exception {
    // TODO Test if sorted
    // String json = service.getPerItem(667, 5510, 2011);
    // System.out.println("PerItem: " + json);
    mockMvc
        .perform(
            get("/peritem?itemid=667&measureid=5510&year=2011").accept(
                MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[0].country_id", greaterThan(1)))
        .andExpect(jsonPath("$[0].value", greaterThan(1d)));
  }

  @Test
  public void testPerCountry() throws Exception {
    // String json = service.getPerCountry(351, 5510, 2011);
    // System.out.println("PerCountry: " + json);
    mockMvc
        .perform(get("/percountry?countryid=351&measureid=5510&year=2011").accept(
                MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[0].item_id", greaterThan(1)))
        .andExpect(jsonPath("$[0].value", greaterThan(1d)));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testPerCountryAndTime() throws Exception {
    String json = service.getPerCountryAndTime(351, 5510, Arrays.asList(new Integer[] { 667, 656 }), 2010, 2011);
    System.out.println("PerCountryAndTime: " + json);
    mockMvc
        .perform(
            get("/percountrytime?countryid=351&measureid=5510&items=667,656&yearfrom=2010&yearto=2011").accept(
                MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[*].item_id", containsInAnyOrder(667, 656)))
        .andExpect(jsonPath("$[0].values", hasSize(2)))
        .andExpect(jsonPath("$[0].values[*].year", containsInAnyOrder(2010, 2011)))
        .andExpect(jsonPath("$[0].values[*].value", containsInAnyOrder(greaterThan(1d), greaterThan(1d))))
        .andExpect(jsonPath("$[1].values", hasSize(2)))
        .andExpect(jsonPath("$[1].values[*].year", containsInAnyOrder(2010, 2011)))
        .andExpect(jsonPath("$[1].values[*].value", containsInAnyOrder(greaterThan(1d), greaterThan(1d))));
  }

}
