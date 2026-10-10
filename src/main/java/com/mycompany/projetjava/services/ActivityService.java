package com.mycompany.projetjava.services;

import com.mycompany.projetjava.DBConnection;
import com.mycompany.projetjava.UserSession;
import com.mycompany.projetjava.models.Activity;
import com.mycompany.projetjava.models.ActivityType;
import com.mycompany.projetjava.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityService {
    
    public void delete(Activity activity) throws SQLException {
        try (Connection connection = DBConnection.getConnection()){
            if(activity.getId() != -1){
                String requete = "DELETE FROM projetjava.activities WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(requete);
                statement.setInt(1, activity.getId());
                statement.executeUpdate();
            }
        }
    }
    
    public boolean add(Activity activity) throws SQLException, Exception {
        if(activity.getId() != -1) throw new Exception("L'activité à ajouter existe déjà !");
        try (Connection connection = DBConnection.getConnection()){
            String requete = "INSERT INTO activities (title, description, type_id, datetime, duration, user_id) VALUES (?, ? ,? ,? ,? ,?)";
            PreparedStatement statement = connection.prepareStatement(requete);
            statement.setString(1, activity.getTitle());
            statement.setString(2, activity.getDescription());
            statement.setInt(3, activity.getType().getId());
            statement.setObject(4, activity.getDateTime());
            statement.setInt(5, activity.getDuration());
            statement.setInt(6, UserSession.getInstance().getId());
            int modifiedLines = statement.executeUpdate();
            return modifiedLines > 0;
        }
    }
    
    public boolean update(Activity activity) throws SQLException, Exception {
        if(activity.getId() == -1) throw new Exception("L'activité modifié n'existe pas !");
        
        try (Connection connection = DBConnection.getConnection()){
            String requete = "UPDATE activities "
                    + "SET title = ?, description = ?, type_id = ?, datetime = ?, duration = ? "
                    + "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(requete);
            statement.setString(1, activity.getTitle());
            statement.setString(2, activity.getDescription());
            statement.setInt(3, activity.getType().getId());
            statement.setObject(4, activity.getDateTime());
            statement.setInt(5, activity.getDuration());
            statement.setInt(6, activity.getId());
            int modifiedLines = statement.executeUpdate();
            return modifiedLines > 0;
        }
    }
    
    public ArrayList<ActivityType> getAllActivityTypes() throws SQLException {
        ArrayList<ActivityType> types = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()){
            String requete = "SELECT id, name FROM projetjava.activity_types";
            PreparedStatement statement = connection.prepareStatement(requete);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                ActivityType element = new ActivityType(id, name);
                types.add(element);
            }
        }
        return types;
    }
    
    public ArrayList<Activity> getAllActivities(boolean others) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()){
            String request = "SELECT a.id, a.title, a.description, at.id AS type_id, at.name AS type_name, a.datetime, a.duration, a.user_id, u.name, u.surname "
                    + "FROM projetjava.activities a "
                    + "JOIN activity_types AS at ON a.type_id = at.id "
                    + "JOIN users AS u ON a.user_id = u.id ";
            if(others){
                request += "WHERE a.user_id != ? ";
            } else{
                request += "WHERE a.user_id = ? ";
            }
            request += "ORDER BY a.datetime DESC";
            
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setInt(1, UserSession.getInstance().getId());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                ActivityType type = new ActivityType(rs.getInt("type_id"), rs.getString("type_name"));
                LocalDateTime datetime = rs.getObject("datetime", LocalDateTime.class);
                int duration = rs.getInt("duration");
                User user = new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("surname"));
                
                Activity activity = new Activity(id, title, description, type, datetime, duration, user);
                activities.add(activity);
            }
        }
        return activities;
    }
}