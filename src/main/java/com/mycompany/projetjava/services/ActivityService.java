package com.mycompany.projetjava.services;

import com.mycompany.projetjava.DBConnection;
import com.mycompany.projetjava.UserSession;
import com.mycompany.projetjava.models.Activity;
import com.mycompany.projetjava.models.ActivityType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    public boolean add(Activity activity) throws SQLException {
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
}