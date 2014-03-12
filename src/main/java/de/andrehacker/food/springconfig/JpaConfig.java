package de.andrehacker.food.springconfig;

import org.springframework.beans.factory.annotation.Value;

/**
 * See 13.5 JPA. http://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/html/orm.html
 *
 */
public class JpaConfig {
  
  @Value("${dataSource.driverClassName}")
  private String driver;
  @Value("${dataSource.url}")
  private String url;
  @Value("${dataSource.username}")
  private String username;
  @Value("${dataSource.password}")
  private String password;
  @Value("${hibernate.dialect}")
  private String dialect;
  @Value("${hibernate.hbm2ddl.auto}")
  private String hbm2ddlAuto;


}
