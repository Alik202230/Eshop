package org.example.service;

import org.example.database.SQLConnectionProvider;
import org.example.model.Category;

import java.sql.*;

public class CategoryService {

  private final Connection connection = SQLConnectionProvider.getInstance().getConnection();

  public void add(Category category) {
    String sql = "INSERT INTO category (name) VALUES (?)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, category.getName());
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        category.setId(resultSet.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Category getCategoryById(int id) {
    String sql = "SELECT * FROM category WHERE id = " + id;
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        return new Category(
            resultSet.getInt("id"),
            resultSet.getString("name")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void updateCategoryById(Category category, int id) {
    String sql = "UPDATE category SET name = ? WHERE id = " + id;
    try(PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, category.getId());
      statement.setString(2, category.getName());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteCategoryById(int id) {
    String sql = "DELETE FROM category WHERE id = " + id;
    try {
      connection.createStatement().executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
