package org.example.Functionalities;

import org.example.DataBaseConnection.DatabaseConnection;
import org.example.INPUTHANDLING.Input;

import java.sql.*;
import java.util.Scanner;

public class AdminPanel {
    DatabaseConnection db = new DatabaseConnection();
    Scanner sc = new Scanner(System.in);
    Input in = new Input();

    public void login() {
        String sql = "SELECT username, password FROM admin WHERE username=?";
        System.out.println("---Admin Login------");
        System.out.println("Enter your username");
        String admin_name = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.getPassword());
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            psmt.setString(1, admin_name);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    String check_pass = rs.getString("password");
                    String username = rs.getString("username");

                    if (password.equals(check_pass) && admin_name.equals(username)) {
                        System.out.println("-----------Login Successful--------");
                        System.out.println("1. Update Prices And Rooms");
                        System.out.println("2. Edit Customer");
                        System.out.println("3. Total Revenue");
                        System.out.println("4. Back");
                        System.out.print("Enter your choice: ");
                        int choice = sc.nextInt();
                        sc.nextLine(); // consume newline

                        if (choice == 1) {
                            update_price_room_name(conn);
                        } else if (choice == 2) {
                            edit_customer(conn);
                        } else if (choice == 3) {
                            total_revenue(conn);
                        } else if (choice == 4) {
                            in.user_menu();
                        }
                    } else {
                        System.out.println("-------Incorrect Password or Username -------");
                    }
                } else {
                    System.out.println("-------Admin Not Found -------");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void edit_customer(Connection conn) {
        System.out.println("--- Edit Section---");
        System.out.println("1. Update Customer");
        System.out.println("2. Remove Customer");
        System.out.print("Enter your choice (1-2): ");
        int ch = sc.nextInt();
        sc.nextLine(); // consume newline

        if (ch == 1) {
            String sql = "UPDATE users SET username=? WHERE username=?";
            try (PreparedStatement psmt2 = conn.prepareStatement(sql)) {
                System.out.print("Enter the current username: ");
                String current_username = sc.nextLine().trim();
                System.out.print("Enter the new username: ");
                String updated_username = sc.nextLine();

                psmt2.setString(1, updated_username);
                psmt2.setString(2, current_username);

                int rows = psmt2.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ Username updated successfully");
                } else {
                    System.out.println("❌ No user found with that username");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else if (ch == 2) {
            String sql = "DELETE FROM users WHERE username=?";
            try (PreparedStatement psmt3 = conn.prepareStatement(sql)) {
                System.out.print("Enter the username you want to remove: ");
                String del_username = sc.nextLine();
                psmt3.setString(1, del_username);

                int rows = psmt3.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ User deleted successfully");
                } else {
                    System.out.println("❌ No user found with that username");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void update_price_room_name(Connection conn) {
        System.out.println("--- Update Price and Room Name ---");
        System.out.println("1. Update Room Price");
        System.out.println("2. Update Room Name");
        System.out.print("Enter your choice (1-2): ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        if (choice == 1) {
            String sql = "UPDATE rooms SET price=? WHERE room_id=?";
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                System.out.print("Enter room ID: ");
                int roomId = sc.nextInt();
                System.out.print("Enter new price: ");
                double newPrice = sc.nextDouble();
                sc.nextLine(); // consume newline

                psmt.setDouble(1, newPrice);
                psmt.setInt(2, roomId);

                int rows = psmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ Room price updated successfully");
                } else {
                    System.out.println("❌ Room not found");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else if (choice == 2) {
            String sql = "UPDATE rooms SET room_name=? WHERE room_id=?";
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                System.out.print("Enter room ID: ");
                int roomId = sc.nextInt();
                System.out.print("Enter new room name: ");
                String newName = sc.nextLine();
                sc.nextLine(); // consume newline in case

                psmt.setString(1, newName);
                psmt.setInt(2, roomId);

                int rows = psmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ Room name updated successfully");
                } else {
                    System.out.println("❌ Room not found");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void total_revenue(Connection conn) {
        String sql = "SELECT SUM(prices) AS total FROM reservation_details";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                double total = rs.getDouble("total");
                System.out.println("💰 Total Revenue: " + total);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
