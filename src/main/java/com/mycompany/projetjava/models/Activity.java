package com.mycompany.projetjava.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Activity {
    private int id = -1;
    private String title;
    private String description;
    private ActivityType type;
    private LocalDateTime datetime;
    private int duration;
    private User user;
    
    public Activity(String title, String description, ActivityType type, LocalDateTime datetime, int duration, User user){
        this.title = title;
        this.description = description;
        this.type = type;
        this.datetime = datetime;
        this.duration = duration;
        this.user = user;
    }
    
    public Activity(int id, String title, String description, ActivityType type, LocalDateTime datetime, int duration, User user){
        this(title, description, type, datetime, duration, user);
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public ActivityType getType(){
        return this.type;
    }
    
    public LocalDateTime getDateTime(){
        return this.datetime;
    }
    
    public int getDuration(){
        return this.duration;
    }
    
    public User getUser(){
        return this.user;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setType(ActivityType type){
        this.type = type;
    }
    
    public void setDateTime(LocalDateTime datetime){
        this.datetime = datetime;
    }
    
    public void setDuration(int duration){
        this.duration = duration;
    }
    
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = this.datetime.format(formatter);
        return formattedDateTime + " | " + this.title;
   }
}
