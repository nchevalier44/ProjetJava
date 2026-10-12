/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetjava;

/**
 *
 * @author miste
 */


import java.util.HashMap;
import java.util.Map;

public class GeoUtils {

    public static class Coordinates {
        public double lat;
        public double lon;

        public Coordinates(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }

    private static final Map<String, Coordinates> CITIES = new HashMap<>();

    static {
        CITIES.put("Nantes", new Coordinates(47.2184, -1.5536));
        CITIES.put("Rennes", new Coordinates(48.1173, -1.6778));
        CITIES.put("Angers", new Coordinates(47.4784, -0.5632));
        CITIES.put("Saint-Nazaire", new Coordinates(47.2736, -2.2137));
        CITIES.put("Vannes", new Coordinates(47.6582, -2.7599));
        CITIES.put("Lorient", new Coordinates(47.7483, -3.3702));
        CITIES.put("Paris", new Coordinates(48.8566, 2.3522));
        CITIES.put("Guérande", new Coordinates(47.3283, -2.4289));
    }

    public static Coordinates getCoordinates(String ville) {
        if (ville != null && CITIES.containsKey(ville)) {
            return CITIES.get(ville);
        }
        
        return new Coordinates(47.2184, -1.5536); //coordonnées de Nantes si inconnu
    }
}

