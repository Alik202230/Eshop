package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnectionProvider {

  private static SQLConnectionProvider instance;

  private Connection connection;

  private SQLConnectionProvider() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static SQLConnectionProvider getInstance() {
    if (instance == null) {
      instance = new SQLConnectionProvider();
    }
    return instance;
  }

  public Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        String JDBC_USERNAME = "root";
        String JDBC_URL = "jdbc:mysql://localhost:3306/eshop_db";
        String JDBC_PASSWORD = "root";
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
    return connection;
  }

}
