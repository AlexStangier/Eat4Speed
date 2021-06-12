package de.eat4speed.FahrerAuswahl_FahrtenVergabe;

import de.eat4speed.entities.Fahrer;

public class Fahrer_Distanz {

    private Fahrer fahrer;
    private int Restaurant_ID;
    private Long Distanz;
    private Long Fahrzeit;

    public Fahrer_Distanz(Fahrer fahrer, int restaurant_ID, Long distanz, Long fahrzeit)
    {
        this.fahrer = fahrer;
        Restaurant_ID = restaurant_ID;
        Distanz = distanz;
        Fahrzeit = fahrzeit;
    }

    public Fahrer getFahrer() { return fahrer; }

    public void setFahrer(Fahrer fahrer) {
        this.fahrer = fahrer;
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
