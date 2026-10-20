package com.mycompany.projetjava;

import com.mycompany.projetjava.ui.AuthPage;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class ProjetJava {
    
    public static void main(String[] args) { 
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Erreur lors de l'initialisation du thème FlatLaf");
        }
        //Ouvre la page d'authentification
        AuthPage pageConnexion = new AuthPage();
        pageConnexion.setVisible(true);
    }
}
