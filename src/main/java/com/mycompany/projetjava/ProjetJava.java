package com.mycompany.projetjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjetJava {
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String lienDB = "jdbc:mysql://localhost:3306/projetjava?zeroDateTimeBehavior=convertToNull";
        Connection con = DriverManager.getConnection(lienDB, "root", "root");
        
        Statement st = con.createStatement();
            
        ResultSet result = st.executeQuery("SELECT * FROM projetjava.users");
        while(result.next()){
            System.out.println(result.getString(2));
        }
    }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }}
}
