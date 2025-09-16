package org.example.Functionalities;

import org.example.DataBaseConnection.DatabaseConnection;

import java.sql.*;

public class ViewRoomAvailability {
    DatabaseConnection db=new DatabaseConnection();



    public void availability(){
        try(Connection conn= DriverManager.getConnection(db.url,db.user,db.password)) {
            String sql="SELECT name, price\n" +
                    "\tFROM public.availability;";
            PreparedStatement psmt=conn.prepareStatement(sql);
            ResultSet rs=psmt.executeQuery();
            System.out.println(" ---ROOMS AND PRICES----");
            while (rs.next()){
                System.out.println("RoomName:"+ rs.getString("name")+ "  |||  "+ "Prices:"+rs.getString("price"));
            }
        }
        catch (SQLException E){
            System.out.println(E);
        }
    }
}