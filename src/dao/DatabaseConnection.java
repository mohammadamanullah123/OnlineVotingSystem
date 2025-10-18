package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/voting_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "aman@ggi"; // Your MySQL password
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("DEBUG: New database connection created");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DEBUG: Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}