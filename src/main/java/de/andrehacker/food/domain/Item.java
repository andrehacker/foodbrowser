package de.andrehacker.food.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("_id")
  private int itemCode;
  
  public Item(int itemCode, String name) {
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
