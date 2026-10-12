package com.mycompany.projetjava.services;

import com.mycompany.projetjava.DBConnection;
import com.mycompany.projetjava.UserSession;
import com.mycompany.projetjava.models.Activity;
import com.mycompany.projetjava.models.ActivityType;
import com.mycompany.projetjava.models.User;
import com.mycompany.projetjava.models.WeeklySummary;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityService {
    
    /**
     * Delete the activity given in parameter in the database
     * @param activity
     * @throws SQLException 
     */
    public void delete(Activity activity) throws SQLException {
        try (Connection connection = DBConnection.getConnection()){
            if(activity.getId() != -1){ // Si l'id est -1, cela signifie qu'elle n'existe pas dans la base de donnée
                String requete = "DELETE FROM projetjava.activities WHERE id=?";
                PreparedStatement statement = connection.prepareStatement(requete);
                statement.setInt(1, activity.getId());
                statement.executeUpdate();
            }
        }
    }
    
    /**
     * Add the activity given in parameter in the database
     * @param activity
     * @return
     * @throws SQLException
     * @throws Exception 
     */
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
    
    /**
     * Update the activity given in parameter
     * @param activity
     * @return
     * @throws SQLException
     * @throws Exception 
     */
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
    
    /**
     * Get all activity types
     * @return
     * @throws SQLException 
     */
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
    
    /**
     * Get all users activities
     * @param others
     * @return
     * @throws SQLException 
     */
    public ArrayList<Activity> getAllActivities(boolean others) throws SQLException {
        ArrayList<Activity> activities = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()){
            // 1. AJOUT de u.sport_favori et u.visibilite dans le SELECT
            String request = "SELECT a.id, a.title, a.description, at.id AS type_id, at.name AS type_name, a.datetime, a.duration, a.user_id, u.name, u.surname, u.ville, u.sport_favori, u.visibilite "
                    + "FROM projetjava.activities a "
                    + "JOIN activity_types AS at ON a.type_id = at.id "
                    + "JOIN users AS u ON a.user_id = u.id ";
            
            if (others) {
                // FLUX GÉNÉRAL :
                // Soit c'est mon activité (a.user_id = ?) -> je la vois toujours
                // Soit l'auteur est public (u.visibilite = 0 ou FALSE) -> je la vois
                // -> Si l'auteur est privé (u.visibilite = 1), seuls lui la voit.
                request += "WHERE (a.user_id = ? OR u.visibilite = 1) ";
            } else {
                // MES ACTIVITÉS :
                // Uniquement les miennes
                request += "WHERE a.user_id = ? ";
            }
            
            request += "ORDER BY a.datetime DESC";
            
            PreparedStatement statement = connection.prepareStatement(request);
            
            // On injecte mon ID utilisateur dans les deux cas
            statement.setInt(1, UserSession.getInstance().getId());
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                ActivityType type = new ActivityType(rs.getInt("type_id"), rs.getString("type_name"));
                LocalDateTime datetime = rs.getObject("datetime", LocalDateTime.class);
                int duration = rs.getInt("duration");
                
                // 2. UTILISATION DU NOUVEAU CONSTRUCTEUR (6 paramètres)
                User user = new User(
                    rs.getInt("user_id"), 
                    rs.getString("name"), 
                    rs.getString("surname"), 
                    rs.getString("ville"),
                    rs.getString("sport_favori"),
                    rs.getBoolean("visibilite")
                );
                
                Activity activity = new Activity(id, title, description, type, datetime, duration, user);
                activities.add(activity);
            }
        }
        return activities;
    }
    
    public WeeklySummary getWeeklySummary() throws SQLException {
        WeeklySummary summary = new WeeklySummary();
        int userId = UserSession.getInstance().getId();

        String sqlStats = "SELECT COUNT(*) AS total_count, COALESCE(SUM(duration), 0) AS total_duration " +
                          "FROM projetjava.activities " +
                          "WHERE user_id = ? " +
                          "AND YEARWEEK(datetime, 1) = YEARWEEK(CURDATE(), 1)";

        String sqlTopSport = "SELECT t.name " +
                             "FROM projetjava.activities a " +
                             "JOIN projetjava.activity_types t ON a.type_id = t.id " +
                             "WHERE a.user_id = ? " +
                             "AND YEARWEEK(a.datetime, 1) = YEARWEEK(CURDATE(), 1) " +
                             "GROUP BY t.name " +
                             "ORDER BY COUNT(*) DESC " +
                             "LIMIT 1";

        try (Connection connection = DBConnection.getConnection()) {
            // Stats globales
            try (PreparedStatement ps = connection.prepareStatement(sqlStats)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        summary.count = rs.getInt("total_count");
                        summary.totalDurationSeconds = rs.getInt("total_duration");
                    }
                }
            }

            // Sport favori
            try (PreparedStatement ps = connection.prepareStatement(sqlTopSport)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        summary.topSport = rs.getString("name");
                    }
                }
            }
        }

        return summary;
    }
}