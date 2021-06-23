package de.eat4speed.dto;

public class BestellungUpdateDto {

    public String status;

    public long auftragsId;

    public BestellungUpdateDto() {
    }

    public BestellungUpdateDto(String status, long auftragsId) {
        this.status = status;
        this.auftragsId = auftragsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAuftragsId() {
        return auftragsId;
    }

    public void setAuftragsId(long auftragsId) {
        this.auftragsId = auftragsId;
    }
}
