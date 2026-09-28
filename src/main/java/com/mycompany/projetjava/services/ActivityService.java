package com.mycompany.projetjava.services;

import com.mycompany.projetjava.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActivityService {
    
    public void delete(int id) throws SQLException {
        try (Connection connection = DBConnection.getConnection()){
            String requete = "DELETE FROM projetjava.activities WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(requete);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}