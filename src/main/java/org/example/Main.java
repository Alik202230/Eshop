package org.example;

import org.example.model.Category;
import org.example.model.Product;
import org.example.service.CategoryService;
import org.example.service.ProductService;

import java.util.Scanner;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);
  private static final CategoryService categoryService = new CategoryService();
  private static final ProductService productService = new ProductService();

  public static void main(String[] args) {
    boolean isRunning = true;
    while (isRunning) {
      printCommands();
      String command = scanner.nextLine();
      switch (command) {
        case "0":
          isRunning = false;
          break;
        case "1":
          addCategory();
          break;
        case "2":
          updateCategory();
          break;
        case "3":
          deleteCategory();
          break;
        case "4":
          addProduct();
          break;
        case "5":
          updateProductById();
          break;
        case "6":
          deleteProductById();
          break;
        case "7":
          printSumOfProduct();
          break;
        case "8":
          printMaxPriceOfProduct();
          break;
        case "9":
          printMinPriceOfProduct();
          break;
        case "10":
          printAvgPriceOfProduct();
          break;
        default:
          System.out.println("Invalid command");
      }
    }
  }

  private static void printAvgPriceOfProduct() {
    double avgPrice = productService.getAvgPriceOfProducts();
    System.out.println("The average price is: " + avgPrice);
  }

  private static void printMinPriceOfProduct() {
    double minPrice = productService.getMinPriceOfProducts();
    System.out.println("Min price: " + minPrice);
  }

  private static void printMaxPriceOfProduct() {
    double maxPrice = productService.getMaxPriceOfProducts();
    System.out.println("Max price: " + maxPrice);
  }

  private static void printSumOfProduct() {
    int totalQuantity = productService.getSumOfProducts();
    System.out.println("Total products: " + totalQuantity);
  }

  private static void deleteProductById() {
    System.out.println("Enter product id: ");
    int productId = Integer.parseInt(scanner.nextLine());
    productService.deleteProductById(productId);
    System.out.println("Product deleted.");
  }

  private static void updateProductById() {
    System.out.println("Please enter product id");
    int productId = Integer.parseInt(scanner.nextLine());
    Product product = productService.getProductById(productId);

    if (!product.getName().isEmpty()) product.setName(product.getName());
    if (!product.getDescription().isEmpty()) product.setDescription(product.getDescription());
    if (product.getPrice() != 0) product.setPrice(product.getPrice());
    if (product.getQuantity() != 0) product.setQuantity(product.getQuantity());
    productService.updateProductById(product, productId);
    System.out.println("Product updated.");
  }

  private static void addProduct() {
    System.out.println("Enter product name, description, price, quantity");
    String productData = scanner.nextLine();
    String[] productArray = productData.split(",");

    String name = productArray[0];
    String description = productArray[1].trim();
    double price = Double.parseDouble(productArray[2]);
    int quantity = Integer.parseInt(productArray[3]);

    System.out.println("please choose category id");
    int id = Integer.parseInt(scanner.nextLine());
    Category category = categoryService.getCategoryById(id);

    if (category == null) {
      System.out.println("Invalid category");
      return;
    }

    Product product = new Product(name, description, price, quantity, category);
    productService.add(product);
    System.out.println("Product added successfully");
  }

  private static void deleteCategory() {
    System.out.println("Please enter category id");
    int categoryId = Integer.parseInt(scanner.nextLine());
    Category category = categoryService.getCategoryById(categoryId);
    if (category != null) {
      categoryService.deleteCategoryById(categoryId);
      System.out.println("Category deleted successfully");
      return;
    }
    System.out.println("Category not found");
  }

  private static void updateCategory() {
    System.out.println("Please enter category id:");
    int id = Integer.parseInt(scanner.nextLine());
    Category categoryId = categoryService.getCategoryById(id);
    if (categoryId != null) {
      System.out.println("Please enter category name:");
      String categoryName = scanner.nextLine();
      Category newCategory = new Category();
      newCategory.setName(categoryName);
      categoryService.updateCategoryById(newCategory, id);
      System.out.println("Category updated successfully");
    }
    System.out.println("Category not found");
  }

  private static void addCategory() {
    System.out.println("Please enter the name of the category: ");
    String categoryName = scanner.nextLine();
    if (!categoryName.isEmpty()) {
      System.out.println("This category already exists!");
      return;
    }
    System.out.println();
    Category category = new Category(categoryName);
    categoryService.add(category);
    System.out.println("Category added successfully!");
  }

  private static void printCommands() {
    System.out.println("Please enter 0 for exit");
    System.out.println("Please enter 1 for add category");
    System.out.println("Please enter 2 for update category");
    System.out.println("Please enter 3 for delete category");
    System.out.println("Please enter 4 for add product");
    System.out.println("Please enter 5 for update product");
    System.out.println("Please enter 6 for delete product");
    System.out.println("Please enter 7 for print sum of product");
    System.out.println("Please enter 8 for print max of product");
    System.out.println("Please enter 9 for print min of product");
    System.out.println("Please enter 10 for print avg of product");
  }


}