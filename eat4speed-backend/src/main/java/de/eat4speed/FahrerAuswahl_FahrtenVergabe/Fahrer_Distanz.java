package de.eat4speed.FahrerAuswahl_FahrtenVergabe;

public class Fahrer_Distanz {

    private int Fahrer_ID;
    private int Restaurant_ID;
    private Long Distanz;
    private Long Fahrzeit;

    public Fahrer_Distanz(int fahrer_ID, int restaurant_ID, Long distanz, Long fahrzeit)
    {
        Fahrer_ID = fahrer_ID;
        Restaurant_ID = restaurant_ID;
        Distanz = distanz;
        Fahrzeit = fahrzeit;
    }

    public int getFahrer_ID() { return Fahrer_ID; }

    public void setFahrer_ID(int fahrer_ID) {
        Fahrer_ID = fahrer_ID;
    }

    public int getRestaurant_ID() { return Restaurant_ID; }

    public void setRestaurant_ID(int restaurant_ID) { Restaurant_ID = restaurant_ID; }

    public Long getDistanz() {
        return Distanz;
    }

    public void setDistanz(Long distzanz) {
        Distanz = distzanz;
    }

    public Long getFahrzeit() {
        return Fahrzeit;
    }

    public void setFahrzeit(Long fahrzeit) {
        Fahrzeit = fahrzeit;
    }
}
