package com.mycompany.projetjava;

import com.mycompany.projetjava.models.User;

public class UserSession {

    private static UserSession instance;
    private User user;

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void setUser(User u){
        this.user = u;
    }

    public int getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }
    
    public boolean isLogged() {
        return user != null;
    }
    
    public void logout() {
        this.user = null;
    }
    
    public User getUser(){
        return this.user;
    }
}