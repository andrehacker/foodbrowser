package de.andrehacker.food.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * For custom json google "spring mvc custom json"
 */
public class Product {
  
  @JsonProperty("product_name")
  private String name;
  
  @JsonProperty("product_code")
  private int itemCode;
  
  public Product(int itemCode, String name) {
    this.name = name;
    this.itemCode = itemCode;
  }
  
  public void setItemCode(int itemCode) {
    this.itemCode = itemCode;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getItemCode() {
    return itemCode;
  }
  
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Product [name=" + name + " itemCode=" + itemCode + "]";
  }
  
}
