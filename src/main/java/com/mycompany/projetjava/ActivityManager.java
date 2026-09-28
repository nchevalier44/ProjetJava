package com.mycompany.projetjava;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityManager {
    
    public void delete(int id){
        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM projetjava.activities WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
}