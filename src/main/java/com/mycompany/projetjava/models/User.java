package com.mycompany.projetjava.models;

public class User {
    private int id;
    private String name;
    private String surname;
    
    public User(int id, String name, String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
    
    public String getName(){
        return this.name + " " + this.surname;
    }
    
    public int getId(){
        return this.id;
    }
}
