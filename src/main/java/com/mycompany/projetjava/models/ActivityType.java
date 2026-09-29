package com.mycompany.projetjava.models;


public class ActivityType {
   private int id;
   private String name;
   
   public ActivityType(int id, String name){
       this.id = id;
       this.name = name;
   }
   
   public int getId(){
       return this.id;
   }
   
   public String getName(){
       return this.name;
   }
   
   @Override
   public String toString(){
       return this.name;
   }
}
