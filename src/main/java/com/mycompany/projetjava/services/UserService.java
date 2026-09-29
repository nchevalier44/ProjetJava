package com.mycompany.projetjava.services;

import com.mycompany.projetjava.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.projetjava.models.User;


public class UserService {
    public User getUser(String id, String password) throws SQLException {
        try (Connection connection = DBConnection.getConnection()){
            String request = "SELECT * FROM users WHERE id = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, id);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                int ID = rs.getInt("id");
                String name = rs.getString("name");
                return new User(ID, name);
            }
            return null;
        }
    }
    
    public boolean addUser(String id, String name, String password) throws SQLException, java.sql.SQLIntegrityConstraintViolationException{
        try (Connection connection = DBConnection.getConnection()){
            String request = "INSERT INTO users (id, name, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, id); 
            statement.setString(2, name);  
            statement.setString(3, password);
            
            int modifiedLines = statement.executeUpdate();
            return modifiedLines > 0;
        }
    }                                
}
