package org.example.Functionalities;
import org.example.DataBaseConnection.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;
public class Login {
    ViewRoomAvailability viewRoomAvailability = new ViewRoomAvailability();
    Reservation reservation=new Reservation();
    Scanner sc = new Scanner(System.in);
    DatabaseConnection db=new DatabaseConnection();
    AdminPanel ad=new AdminPanel();


    public void login() {
        System.out.println("-------Login Section----");
        System.out.println("1.Login as Admin");
        System.out.println("2. Login as Customer");
        Integer choices=sc.nextInt();
        if (choices==1){
            ad.login();
        } else if (choices==2) {

            try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
                System.out.println("Enter you username");
                String inputname = sc.nextLine().strip();
                System.out.println("Enter your password");
                String inputpassword = sc.nextLine().strip();
                String sql = "SELECT password FROM users WHERE username = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, inputname);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String storepassword = rs.getString("password");
                    if (storepassword.equals(inputpassword)) {
                        System.out.println("Login Successful. Welcome " + inputname + "to our hotel ");
                        options();
                    } else {
                        System.out.println("Incorrect Password or username");
                    }

                } else {
                    System.out.println("Invalid user");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void options() {
        System.out.println("1.----View Room Availability-----");
        System.out.println("2.------Reserve Room----------");
        System.out.println("Enter your choice");
        Integer choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Now you can view room availabilities");
            viewRoomAvailability.availability();
        } else if (choice==2) {
            reservation.reservation();
        }
    }
}