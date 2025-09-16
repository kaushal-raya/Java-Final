package org.example.Functionalities;

import org.example.DataBaseConnection.DatabaseConnection;
import org.example.DataBaseConnection.Pass;

import java.sql.*;
import java.util.Scanner;

public class Signup {
    Scanner sc=new Scanner(System.in);
    DatabaseConnection db=new DatabaseConnection();



    public void signup() {
        while (true) {
            try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
                System.out.println("------SIGNUP SECTION--------");
                System.out.println("Enter your username :");
                String username = sc.next().trim().strip();
                System.out.println("Enter your email :");
                String email = sc.next().trim().strip();
                System.out.println("Enter your password (>8characters) :");
                String initialpass = sc.next().strip().trim();
                String confirmpass;
                if (initialpass.length() <= 8) {
                    System.out.println("Password must be greater than 8 digit ");
                    break;
                } else {
                    System.out.println("Confirm your password :");
                    confirmpass = sc.next().strip().trim();
                }
                if (!initialpass.equals(confirmpass)) {
                    System.out.println("Password does not match");
                    return;
                }

                // Check if username already exists
                String checkSql = "SELECT 1 FROM public.users WHERE username = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, username);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("Username already exists. Please choose another.");
                            return;
                        }
                    }
                }
                // Insert new user
                String insertSql = "INSERT INTO public.users(username, password, email) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, initialpass);
                    insertStmt.setString(3, email);
                    int rows = insertStmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("User successfully registered.");
                        System.out.println("-----Now Login to use this app----");
                        Login login = new Login();
                        login.login();
                    } else {
                        System.out.println("Error occurred during registration.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}