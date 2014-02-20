package de.andrehacker.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.andrehacker.food.service.FAOService;

@Controller
public class FAOController {
  
  FAOService faoService;

  /*
   * Autowired will inject the desired implementation automatically.
   * Assumes that there is only one implementation (otherwise what?).
   * Uses classpath scanning for annotations, e.g. @Service.
   * Important: This needs @ComponentScan or <context:component-scan ...> specified!
   */
  @Autowired
  FAOController(FAOService faoService) {
    this.faoService = faoService;
  }
  

  /*
   * When the @ResponseBody annotation is used, Spring will return the data in a
   * format that is acceptable to the client. That is, if the client request has
   * a header to accept json and Jackson-Mapper is present in the classpath,
   * then Spring will try to serialize the return value to JSON. I guess It
   * implicitly uses the ObjectMapper class from Jackson.
   * 
   * We could also additionally define the params at the RequestMapping, not
   * sure why needed.
   * 
   * All @RequestParam parameters are mandatory
   * 
   * @ResponseBody: method return value directly used as body (no view)
   */
  @RequestMapping("items")
  @ResponseBody
  public String getItems() {
    return faoService.getItems();
  }
  
  @RequestMapping("countries")
  @ResponseBody
  public String getCountries() {
    return faoService.getCountries();
  }

  @RequestMapping("measures")
  @ResponseBody
  public String getMeasures() {
    return faoService.getMeasures();
  }

  @RequestMapping("years")
  @ResponseBody
  public String getYears() {
    return faoService.getYears();
  }

  /*
   * http://localhost:8080/stats/peritem?itemid=123&measureid=2&year=2011
   */
	@RequestMapping("peritem")
	@ResponseBody
  public String perItem(
      @RequestParam("itemid") int itemID,
      @RequestParam("measureid") int measureID,
      @RequestParam int year) {
	  
    return faoService.getPerItem(itemID, measureID, year);
  }

  @RequestMapping("percountry")
  @ResponseBody
  public String perCountry(
      @RequestParam("countryid") int countryID,
      @RequestParam("measureid") int measureID,
      @RequestParam int year) {

    return faoService.getPerCountry(countryID, measureID, year);
  }

  @RequestMapping("percountrytime")
  @ResponseBody
  public String perCountryAndTime(
      @RequestParam("countryid") int countryID,
      @RequestParam("measureid") int measureID,
      @RequestParam List<Integer> items,
      @RequestParam("yearfrom") int yearFrom,
      @RequestParam("yearto") int yearTo) {

    return faoService.getPerCountryAndTime(countryID, measureID, items, yearFrom, yearTo);
  }
  
}
