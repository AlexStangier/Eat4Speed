package de.eat4speed.FahrerAuswahl_FahrtenVergabe;

public class Benachrichtigung_Fahrer_dto {

    private int Fahrernummer;
    private String Benachrichtigung;
    private int Auftrags_ID;

    public Benachrichtigung_Fahrer_dto(int fahrernummer, String benachrichtigung, int auftrags_ID) {
        Fahrernummer = fahrernummer;
        Benachrichtigung = benachrichtigung;
        Auftrags_ID = auftrags_ID;
    }

    public int getFahrernummer() {
        return Fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        Fahrernummer = fahrernummer;
    }


    public String getBenachrichtigung() {
        return Benachrichtigung;
    }

    public void setBenachrichtigung(String benachrichtigung) {
        Benachrichtigung = benachrichtigung;
    }

    public int getAuftrags_ID() {
        return Auftrags_ID;
    }

    public void setAuftrags_ID(int auftrags_ID) {
        Auftrags_ID = auftrags_ID;
    }
}
