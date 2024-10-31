package org.example.service;

import org.example.database.SQLConnectionProvider;
import org.example.model.Product;

import java.sql.*;

public class ProductService {

  private Connection connection = SQLConnectionProvider.getInstance().getConnection();
  private CategoryService categoryService = new CategoryService();

  public void add(Product product) {
    String sql = "INSERT INTO product(name, description, price, quantity, category_id) VALUES(?,?,?,?,?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      executeQuery(preparedStatement, product);
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        int id = generatedKeys.getInt(1);
        product.setId(id);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Product getProductById(int id) {
    String sql = "SELECT * FROM product WHERE id = " + id;
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        Product product = new Product(
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getDouble("price"),
            resultSet.getInt("quantity"),
            categoryService.getCategoryById(resultSet.getInt("category_id"))
        );
        product.setId(id);
        return product;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void updateProductById(Product product, int id) {
    String sql = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE id = " + id;
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      executeQuery(statement, product);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteProductById(int id) {
    String sql = "DELETE FROM product WHERE id = " + id;
    try {
      connection.createStatement().executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int getSumOfProducts() {
    String sql = "SELECT SUM(quantity) AS totalQuantity  FROM product";
    int totalQuantity = 0;

    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        totalQuantity = resultSet.getInt("totalQuantity");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return totalQuantity;
  }

  public double getMaxOfProducts() {
    String sql = "SELECT MAX(price) AS maxPrice FROM product";
    int maxPrice = 0;
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        maxPrice = resultSet.getInt("maxPrice");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return maxPrice;
  }

  public double getMinOfProducts() {
    String sql = "SELECT MIN(price) AS minPrice FROM product";
    double minPrice = 0;
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        minPrice = resultSet.getInt("minPrice");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return minPrice;
  }

  public double getAvgOfProducts() {
    String sql = "SELECT AVG(price) AS avgPrice FROM product";
    double avgPrice = 0;
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        avgPrice = resultSet.getInt("avgPrice");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return avgPrice;
  }

  private void executeQuery(PreparedStatement statement, Product product) throws SQLException {
    statement.setString(1, product.getName());
    statement.setString(2, product.getDescription());
    statement.setDouble(3, product.getPrice());
    statement.setInt(4, product.getQuantity());
    statement.setInt(5, product.getCategory().getId());
    statement.executeUpdate();
  }
}
