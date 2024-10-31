package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
  private int id;
  private String name;
  private String description;
  private double price;
  private int quantity;
  private Category category;

  public Product(String name, String description, double price, int quantity, Category category) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
  }

}
