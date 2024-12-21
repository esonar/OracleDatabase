package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BookInserter {

    public static void main(String[] args) {
        // JDBC URL for connecting to the Oracle database
        String jdbcURL = "jdbc:oracle:thin:@13.61.141.194:1521:XE"; // Replace <Public-IP> with your actual database IP
        String username = "SYSTEM"; // Database username
        String password = "AnyPassword2024"; // Database password

        // SQL query to insert data
        String insertSQL = "INSERT INTO BOOK (NAME, ISBN) VALUES (?, ?)";

        // Start connection
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Turn off auto-commit for batch processing
            connection.setAutoCommit(false);

            // Insert 100 rows
            for (int i = 1; i <= 100; i++) {
                preparedStatement.setString(1, "Book " + i); // Set NAME
                preparedStatement.setString(2, "ISBN-" + i); // Set ISBN
                preparedStatement.addBatch();
            }

            // Execute the batch and commit
            preparedStatement.executeBatch();
            connection.commit(); // Commit the transaction

            System.out.println("100 records inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace(); // Print the exception if something goes wrong
        }
    }
}
