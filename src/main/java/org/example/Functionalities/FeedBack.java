package org.example.Functionalities;
import org.example.DataBaseConnection.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;
public class FeedBack {
    String user_name;
    String rating;
    String feed_back;
    String user_id;
    String quality;
    DatabaseConnection db=new DatabaseConnection();
    Scanner sc=new Scanner(System.in);



    public void feedback(){
        try (Connection conn= DriverManager.getConnection(db.url,db.user,db.password)){
            System.out.println("------Feed Back Section-------");
            System.out.println("Enter your name");
            user_name=sc.next();
            System.out.println("Enter your rating like this (*****) max 5");
            rating= sc.next().trim().strip();
            if(rating.length()>5){
                System.out.println("Only rate up to 5");
            }
            System.out.println("Give your feedback : > ");
            feed_back=sc.next();

            //Insert Queery
            String sql="INSERT INTO public.feedback(\n" +
                    "\tusername, ratings, feed_back, user_id, \"Sentiment_Label\")\n" +
                    "\tVALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement psmt =conn.prepareStatement(sql)){
                psmt.setString(1,user_name);
                psmt.setString(2,rating);
                psmt.setString(3,feed_back);
                psmt.setString(4,user_id());
                psmt.setString(5,qualitys());

                Integer rows=psmt.executeUpdate();
                if(rows>0)
                {
                    System.out.println(" 1 Inserted sucessfully");
                }
                else {
                    System.out.println("Something went wrong");
                }
            }
        }
        catch (SQLException error){
            System.out.println(error);

        }

    }
    public String user_id(){
        try (Connection conn=DriverManager.getConnection(db.url,db.user,db.password)){
            String sql="SELECT customer_id FROM reservation_details WHERE customer= ?";
            PreparedStatement psmt2=conn.prepareStatement(sql);
            psmt2.setString(1,user_name);
            ResultSet rs2=psmt2.executeQuery();
            if (rs2.next()){
               user_id  = rs2.getString("customer_id");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return user_id;
    }
    public  String qualitys(){
        if (rating.length()<=2){
            quality="Dissatisfied";
        } else if (rating.length()>=3) {
            quality="Neutral";
        } else if (rating.length()>3) {
            quality="Satisfied";
        }
        else {
            System.out.println("Invalid");
        }
        return quality;
    }
}