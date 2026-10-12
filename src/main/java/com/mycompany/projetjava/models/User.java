package com.mycompany.projetjava.models;

public class User {
    private int id;
    private String name;
    private String surname;
    private String ville;
    private String sportFavori;
    private boolean visibilite;
    
    /**
     * Constructeur complet de l'utilisateur
     */
    public User(int id, String name, String surname, String ville, String sportFavori, boolean visibilite) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.ville = ville;
        this.sportFavori = sportFavori;
        this.visibilite = visibilite;
    }
    
    
    public int getId() {
        return this.id;
    }

    /**
     * Retourne le nom complet (Prénom + Nom)
     */
    public String getName() {
        return this.name + " " + this.surname;
    }
    
    /**
     * Retourne uniquement le prénom
     */
    public String getFirstName() {
        return this.name;
    }
    
    /**
     * Retourne uniquement le nom de famille
     */
    public String getSurname() {
        return this.surname;
    }
    
    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSportFavori() {
        return this.sportFavori;
    }

    public void setSportFavori(String sportFavori) {
        this.sportFavori = sportFavori;
    }

    public boolean isVisibilite() {
        return this.visibilite;
    }

    public void setVisibilite(boolean visibilite) {
        this.visibilite = visibilite;
    }
}