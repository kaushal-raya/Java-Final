package org.example.DataBaseConnection;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class DatabaseConnection {
    Pass ps=new Pass();
    public String url = "jdbc:postgresql://localhost:5050/Hotel";
    public String user = "postgres";  // your username
    public String password = ps.getPassword();
    public String getUrl() {
        return url;
    }
    public String getUser() {
        return user;
    }


    public String getPassword() {
        return password;
    }
}



