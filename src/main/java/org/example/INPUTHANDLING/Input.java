package org.example.INPUTHANDLING;

import org.example.Functionalities.Login;
import org.example.Functionalities.Signup;

import java.sql.SQLException;
import java.util.Scanner;
public class Input {
    Scanner sc = new Scanner(System.in);
    Login login=new Login();
    Signup signup=new Signup();




    public void user_menu() {
        while (true) {
            System.out.println("Hotel Reservation System");
            System.out.println("1-----Login-----");
            System.out.println("2-----Sign Up----");
            System.out.println("3----- Exit--------");
            System.out.println("Your choice=>?");
            Integer choice = sc.nextInt();
            if (choice == 1 ) {
                login.login();
            } else if (choice == 2) {
                signup.signup();
            } else if (choice==3) {
                System.out.println("---------Thank you for visiting our app---");
                break;
            }
            else {
                System.out.println("----Choose again----- ");
            }
        }
    }
}
