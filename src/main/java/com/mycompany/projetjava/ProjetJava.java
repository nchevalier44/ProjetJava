package com.mycompany.projetjava;

import com.formdev.flatlaf.FlatLightLaf;

public class ProjetJava {
    
    public static void main(String[] args) { 
        FlatLightLaf.setup();
        
        AuthPage pageConnexion = new AuthPage();
        pageConnexion.setVisible(true);
    }
}
