package org.example;
import org.example.INPUTHANDLING.Input;

import java.text.DecimalFormat;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  {
        double num = 12345.6789;

        DecimalFormat df = new DecimalFormat("###,###.##");
        System.out.println(df.format(num)); // Output: 12,345.68
        Input input=new Input();
        input.user_menu();


    }
}