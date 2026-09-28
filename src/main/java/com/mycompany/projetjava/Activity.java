package com.mycompany.projetjava;

import java.time.LocalDateTime;


public class Activity {
    private int id;
    private String title;
    private String description;
    private String type;
    private LocalDateTime datetime;
    private int duration;
    
    public Activity(int id, String title, String description, String type, LocalDateTime datetime, int duration){
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.datetime = datetime;
        this.duration = duration;
    }
}
