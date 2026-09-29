package com.mycompany.projetjava.models;

import java.time.LocalDateTime;



public class Activity {
    private int id = -1;
    private String title;
    private String description;
    private ActivityType type;
    private LocalDateTime datetime;
    private int duration;
    
    public Activity(String title, String description, ActivityType type, LocalDateTime datetime, int duration){
        this.title = title;
        this.description = description;
        this.type = type;
        this.datetime = datetime;
        this.duration = duration;
    }
    
    public Activity(int id, String title, String description, ActivityType type, LocalDateTime datetime, int duration, int userID){
        this(title, description, type, datetime, duration);
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
    
    
}
