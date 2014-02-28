package de.andrehacker.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller is a specialization of Component, which will be detected
 * automatically during classpath scanning
 */
@Controller
public class MainController {

  /*
   * Annotation to map a web request (here /home) to this handler method.
   * Multiple return value types are possible. Here we return a String value, which
   * is interpreted as view name
   */
  @RequestMapping("home")
  public String loadHomePage(Model m) {
    // Populate the model.
    m.addAttribute("name", "andre");

    /*
     * Name of the view. InternalResourceViewResolver in java configuration
     * defines the prefix and suffix, so we get /WEB-INF/view/home.jps in our
     * case
     */
    String viewName = "home";
    return viewName;
  }

  // New homepage
  @RequestMapping("/")
  public String loadIndex(Model m) {
    return "index";
  }
}
