package de.andrehacker.food.springconfig;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import de.andrehacker.food.service.FAOServiceMongo;

/*
 * Configuration: This is a configuration class, similar to beans xml class
 * EnableWebMvc: Do some "MVC" magic, similar to <mvc:annotation-driven/>
 * 
 * ComponentScan: tells Spring to look for classes annotated with a Spring 
 * 	 stereotype annotation (@Service, @Component, @Repository, @Controller)
 *   In our case the package holds a Controller.
 *   Also important for DI, e.g. for @AutoWired
 *   
 *  WebMvcConfigurerAdapter allows us to overwrite classes
 *  
 *  TODO Mix with xml configuration, just for fun...
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "de.andrehacker.food")
public class WebConfig extends WebMvcConfigurerAdapter {
  
  private static final String MONGO_HOST = "localhost";
  private static final int MONGO_PORT = 27017;
  private static final String MONGO_DB_NAME = "food";
  private static final boolean MONGO_AUTH_REQUIRED = false;
  private static final String MONGO_USER = "admin";
  private static final String MONGO_PASS = "3CBVH8Q_6mE7";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /*
     * Mapping from /resources folder (in root folder "webapp") to "/resources" folder.
     * Resources, e.g. a css file, can then be used e.g. from html, under the specified path.
     */
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }
  
  /**
   * By default the bean name is the same as the method name. 
   */
  @Bean
  public RestTemplate restTemplate () {
    RestTemplate rest = new RestTemplate();
    // Attention, notice the 2 in the jackson converter
    // No need to register, automatically done!
    //rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    System.out.println("The following converters are registered:");
    for (HttpMessageConverter<?> conv : rest.getMessageConverters()) {
      System.out.println("- " + conv.getClass().getSimpleName());
    }
    return rest;
  }

  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/view/");
    //resolver.setSuffix(".jsp");
    resolver.setSuffix("");
    return resolver;
  }
  
  /**
   * If one bean depends on the other we can simply use the getter
   */
  @Bean
  public FAOServiceMongo faoServiceMongo() throws UnknownHostException {
    return new FAOServiceMongo(mongoDB());
  }
  
  @Bean
  public DB mongoDB() throws UnknownHostException {
    DB db = new MongoClient(MONGO_HOST, MONGO_PORT).getDB(MONGO_DB_NAME);
    if (MONGO_AUTH_REQUIRED) {
      db.authenticate(MONGO_USER, MONGO_PASS.toCharArray());
    }
    return db;
  }
}
