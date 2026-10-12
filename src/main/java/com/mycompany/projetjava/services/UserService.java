package com.mycompany.projetjava.services;

import com.mycompany.projetjava.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.projetjava.models.User;

public class UserService {
    
    /**
     * Get the user informations if the credentials are correct
     * @param id
     * @param password
     * @return
     * @throws SQLException 
     */
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
                String ville = rs.getString("ville");
                String sportFavori = rs.getString("sport_favori");
                boolean visibilite = rs.getBoolean("visibilite");

                User u = new User(ID, name, surname, ville, sportFavori, visibilite);
                return u;
            }
            return null;
        }
    }
    
    /**
     * Create a new user account
     * @param username
     * @param name
     * @param surname
     * @param password
     * @param sport_favori
     * @param visibilite
     * @param ville
     * @return
     * @throws SQLException
     * @throws java.sql.SQLIntegrityConstraintViolationException 
     */
    public boolean addUser(String username, String name, String surname, String password, String sport_favori, boolean visibilite, String ville) throws SQLException, java.sql.SQLIntegrityConstraintViolationException{
        try (Connection connection = DBConnection.getConnection()){
            String request = "INSERT INTO users (username, name, surname, password, sport_favori, visibilite, ville) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    
    /**
     * Update user settings (ville, sport favori, visibilité) and optionally the password
     * @param userId
     * @param ville
     * @param sportFavori
     * @param visibilite
     * @param newPassword (can be null or empty if no change)
     * @return
     * @throws SQLException 
     */
    public boolean updateUserSettings(int userId, String ville, String sportFavori, boolean visibilite, String newPassword) throws SQLException {
        boolean changePassword = (newPassword != null && !newPassword.trim().isEmpty());
        
        String sql = changePassword
            ? "UPDATE users SET ville = ?, sport_favori = ?, visibilite = ?, password = ? WHERE id = ?"
            : "UPDATE users SET ville = ?, sport_favori = ?, visibilite = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ville);
            ps.setString(2, sportFavori);
            ps.setBoolean(3, visibilite);

            if (changePassword) {
                ps.setString(4, newPassword.trim());
                ps.setInt(5, userId);
            } else {
                ps.setInt(4, userId);
            }

            int modifiedLines = ps.executeUpdate();
            return modifiedLines > 0;
        }
    }
}