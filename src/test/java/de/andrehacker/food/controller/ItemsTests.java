package de.andrehacker.food.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import de.andrehacker.food.service.FAOService;
import de.andrehacker.food.springconfig.WebConfig;

/**
 * Based on
 * http://www.javacodegeeks.com/2013/08/unit-testing-of-spring-mvc-controllers-rest-api.html 
 * and
 * https://spring.io/guides/tutorials/rest/2/
 * and 
 * http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/testing.html  
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  // caused null pointer exception
@ContextConfiguration(classes = {WebConfig.class})
public class ItemsTests {
  
  MockMvc mockMvc;
  
  @InjectMocks
  FAOController productsController;
  
  // User the real service - we could also use a mock.
  @Autowired
  FAOService productsService;
  
  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  @Ignore
  public void testProductService() throws Exception {
    // Didn't work yet, configuration problem
    mockMvc.perform(get("/productsnew").accept(MediaType.APPLICATION_JSON))
    .andDo(print())
    .andExpect(status().isOk()); 
  }
  
}
