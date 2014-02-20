package de.andrehacker.food.service;

import java.util.List;

/**
 * TODO Define contract (expected result, ordering guarantees, etc.)
 */
public interface FAOService {
  
  public String getItems();
  public String getMeasures();
  public String getYears();
  public String getCountries();
  public String getPerItem(int itemID, int measureID, int year);
  public String getPerCountry(int countryID, int measureID, int year);
  public String getPerCountryAndTime(int countryID, int measureID, List<Integer> items, int yearFrom, int yearTo);
  
}
