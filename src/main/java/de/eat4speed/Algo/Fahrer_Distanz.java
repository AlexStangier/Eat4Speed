package de.eat4speed.Algo;

public class Fahrer_Distanz {

    private int Fahrer_ID;
    private int Auftrag_ID;
    private Long Distanz;
    private Long Fahrzeit;

    public Fahrer_Distanz(int fahrer_ID, int auftrag_ID, Long distanz, Long fahrzeit)
    {
        Fahrer_ID = fahrer_ID;
        Auftrag_ID = auftrag_ID;
        Distanz = distanz;
        Fahrzeit = fahrzeit;
    }

    public int getFahrer_ID() { return Fahrer_ID; }

    public void setFahrer_ID(int fahrer_ID) {
        Fahrer_ID = fahrer_ID;
    }

    public int getAuftrag_ID() { return Auftrag_ID; }

    public void setAuftrag_ID(int auftrag_ID) { Auftrag_ID = auftrag_ID; }

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
