package org.example.Functionalities;

import org.example.DataBaseConnection.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Reservation {
    Scanner sc = new Scanner(System.in);
    ViewRoomAvailability viewRoomAvailability = new ViewRoomAvailability();
    DatabaseConnection db=new DatabaseConnection();
    FeedBack feedBack=new FeedBack();



    public void reservation() {
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            System.out.println("---------Booking Section-----");
            System.out.println("Enter your name");
            String name = sc.nextLine().strip().trim();
            System.out.println("Enter your email");
            String email = sc.nextLine().strip().trim();
            System.out.println("Enter the room you want to choose.");
            System.out.println("Available Rooms With Prices");
            viewRoomAvailability.availability();
            System.out.println("Enter the room you want to book : ");
            String room = sc.next().trim();
            System.out.print("Enter check-in date (yyyy-mm-dd): ");
            String check_in = sc.next().trim();
            System.out.print("Enter check-out date (yyyy-mm-dd): ");
            String checkout = sc.next().trim();
            String id = customeidIgenerator();
            //Insert Section
            String sql = "INSERT INTO public.reservation_details(\n" +
                    "\tcustomer_id, customer, email, room_name, price, check_in_date, check_out_date)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement insert = conn.prepareStatement(sql)) {
                insert.setString(1, id);
                insert.setString(2, name);
                insert.setString(3, email);
                insert.setString(4, room);
                insert.setString(6,check_in);
                insert.setString(7,checkout);
                String insertsql = "SELECT price FROM availability WHERE name = ?";
                PreparedStatement psmt = conn.prepareStatement(insertsql);
                psmt.setString(1, room);
                ResultSet rs = psmt.executeQuery();
                if (rs.next()) {
                    String room_price = rs.getString("price");
                    insert.setString(5, room_price);
                    Integer rows = insert.executeUpdate();
                    if (rows > 0) {
                        System.out.println(rows + "rows has been inserted successfully");
                        System.out.println("Do you want to give some feedbacks (y/n) =>");
                        String choice=sc.next().trim().strip();
                        if (choice.equals("y") ||choice.equals("Y")){
                            feedBack.feedback();
                        }
                        else {
                            System.out.println("Thank you for your reservation ");
                        }

                    } else {
                        System.out.println("Something went wrong");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e);

        }
    }
    public String customeidIgenerator() {
        Random rn = new Random();
        Integer id = rn.nextInt(1234, 10000);
        String final_id = id.toString();
        return final_id;
    }
}