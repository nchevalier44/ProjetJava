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
            String request = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setString(1, id);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                int ID = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");

                User u = new User(ID, name, surname);
                u.setVille(rs.getString("ville")); // <-- On stocke la ville lue depuis la BDD
                return u;
            }
            return null;
        }
    }
    
    public boolean addUser(String username, String name, String surname, String password, String sport_favori, boolean visibilite, String ville) throws SQLException, java.sql.SQLIntegrityConstraintViolationException{
        
        try (Connection connection = DBConnection.getConnection()){
            String request = "INSERT INTO users (username, name, surname, password, sport_favori, visibilite, ville) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(request);
            
            statement.setString(1, username);
            statement.setString(2, name);     
            statement.setString(3, surname);  
            statement.setString(4, password); 
            statement.setString(5, sport_favori); 
            statement.setBoolean(6, visibilite); 
            statement.setString(7, ville);
            
            int modifiedLines = statement.executeUpdate();
            return modifiedLines > 0;
        }
    }                    
}
