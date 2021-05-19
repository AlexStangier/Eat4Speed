package de.eat4speed.Algo;

public class Fahrer_Distanz {

    private int Fahrer_ID;
    private Long Distanz;

    public Fahrer_Distanz(int fahrer_ID, Long distanz) {
        Fahrer_ID = fahrer_ID;
        Distanz = distanz;
    }

    public int getFahrer_ID() {

        return Fahrer_ID;
    }

    public void setFahrer_ID(int fahrer_ID) {
        Fahrer_ID = fahrer_ID;
    }

    public Long getDistanz() {
        return Distanz;
    }

    public void setDistanz(Long distzanz) {
        Distanz = distzanz;
    }


}
