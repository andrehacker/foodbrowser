package de.andrehacker.food.springconfig;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import de.andrehacker.food.ComponentScanMarker;
import de.andrehacker.food.service.FAOServiceMongo;

/*
 * Configuration: This is a configuration class used by IoC container as a source 
 * for bean definitions, similar to beans xml class.
 * 
 * EnableWebMvc: Do some "MVC" magic, similar to <mvc:annotation-driven/>
 * 
 * ComponentScan: tells Spring to look for classes annotated with a Spring stereotype 
 * annotation (@Component, or a specialization @Service, @Controller, @Repository). In our case the package 
 * holds a Controller. This allows usage of @Inject or @Autowired without manually creating a bean.
 * I could have also used the basePackages property, but this is not type safe. 
 *   
 * WebMvcConfigurerAdapter allows us to overwrite classes
 *  
 * Property files are embedded in the war file, and not externalised. This is done via Maven 
 * profiles. This is against Maven conventions (the output of a built should always be the same,
 * regardless of the profile), but simpler in our case.   
 *  
 * TODO Mix with xml configuration, just for fun...
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
@PropertySource("classpath:mongodb.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

  private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/view/";
  private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

  @Autowired
  private Environment env;

  @Value("${mongo_host}")
  String MONGO_HOST; // "127.10.126.130"
  @Value("${mongo_port}")
  private int MONGO_PORT; // 27017;
  @Value("${mongo_db_name}")
  private String MONGO_DB_NAME; // "foodbrowser";
  @Value("${mongo_auth_required}")
  private boolean MONGO_AUTH_REQUIRED; // true;
  @Value("${mongo_user:\"\"}")
  private String MONGO_USER;
  @Value("${mongo_pass:\"\"}")
  private String MONGO_PASS;

  /*
   * Required to support loading from properties, e.g. via @Value annotation
   * PropertySourcesPlaceholderConfigurer replaces the older
   * PropertyPlaceholderConfigurer since Spring 3.1.
   * 
   * It is used by default to support the property-placeholder element in
   * working against the spring-context-3.1 XSD, whereas spring-context versions
   * <= 3.0 default to PropertyPlaceholderConfigurer to ensure backward
   * compatibility
   * 
   * See
   * http://docs.spring.io/spring/docs/3.1.x/javadoc-api/org/springframework/
   * context /support/PropertySourcesPlaceholderConfigurer.html
   * 
   * We could also use Environment.getProperty. Nice article:
   * http://www.summa-tech
   * .com/blog/2009/04/20/6-tips-for-managing-property-files-with-spring/
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    // Even if set to false, it will not throw an exception if we defined a
    // default value
    configurer.setIgnoreResourceNotFound(false);
    return configurer;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /*
     * Mapping from /resources folder (in root folder "webapp") to "/resources"
     * folder. Resources, e.g. a css file, can then be used e.g. from html,
     * under the specified path.
     */
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/theme/");
  }

  /**
   * By default the bean name is the same as the method name.
   */
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate rest = new RestTemplate();
    // Attention, notice the 2 in the jackson converter
    // No need to register, automatically done!
    // rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    System.out.println("The following converters are registered:");
    for (HttpMessageConverter<?> conv : rest.getMessageConverters()) {
      System.out.println("- " + conv.getClass().getSimpleName());
    }
    return rest;
  }

  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix(VIEW_RESOLVER_PREFIX);
    resolver.setSuffix(VIEW_RESOLVER_SUFFIX);
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
