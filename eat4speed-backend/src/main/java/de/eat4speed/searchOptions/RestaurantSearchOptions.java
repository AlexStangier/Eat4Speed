package de.eat4speed.searchOptions;

import java.util.List;
import java.util.Objects;

public class RestaurantSearchOptions {
    private String restaurantName = "";
    private int kundennummer;
    private double maxMindestbestellwert;
    private double maxEntfernung;
    private int minBewertung;
    private boolean useName;
    private boolean useMindestbestellwert;
    private boolean useEntfernung;
    private boolean useBewertung;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    public double getMaxMindestbestellwert() {
        return maxMindestbestellwert;
    }

    public void setMaxMindestbestellwert(double maxMindestbestellwert) {
        this.maxMindestbestellwert = maxMindestbestellwert;
    }

    public double getMaxEntfernung() {
        return maxEntfernung;
    }

    public void setMaxEntfernung(double maxEntfernung) {
        this.maxEntfernung = maxEntfernung;
    }

    public int getMinBewertung() {
        return minBewertung;
    }

    public void setMinBewertung(int minBewertung) {
        this.minBewertung = minBewertung;
    }

    public boolean isUseName() {
        return useName;
    }

    public void setUseName(boolean useName) {
        this.useName = useName;
    }

    public boolean isUseMindestbestellwert() {
        return useMindestbestellwert;
    }

    public void setUseMindestbestellwert(boolean useMindestbestellwert) {
        this.useMindestbestellwert = useMindestbestellwert;
    }

    public boolean isUseEntfernung() {
        return useEntfernung;
    }

    public void setUseEntfernung(boolean useEntfernung) {
        this.useEntfernung = useEntfernung;
    }

    public boolean isUseBewertung() {
        return useBewertung;
    }

    public void setUseBewertung(boolean useBewertung) {
        this.useBewertung = useBewertung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantSearchOptions that = (RestaurantSearchOptions) o;
        return kundennummer == that.kundennummer && Double.compare(that.maxMindestbestellwert, maxMindestbestellwert) == 0 && Double.compare(that.maxEntfernung, maxEntfernung) == 0 && minBewertung == that.minBewertung && useName == that.useName && useMindestbestellwert == that.useMindestbestellwert && useEntfernung == that.useEntfernung && useBewertung == that.useBewertung && Objects.equals(restaurantName, that.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName, kundennummer, maxMindestbestellwert, maxEntfernung, minBewertung, useName, useMindestbestellwert, useEntfernung, useBewertung);
    }

    @Override
    public String toString() {
        return "RestaurantSearchOptions{" +
                "restaurantName='" + restaurantName + '\'' +
                ", kundennummer=" + kundennummer +
                ", maxMindestbestellwert=" + maxMindestbestellwert +
                ", maxEntfernung=" + maxEntfernung +
                ", minBewertung=" + minBewertung +
                ", useName=" + useName +
                ", useMindestbestellwert=" + useMindestbestellwert +
                ", useEntfernung=" + useEntfernung +
                ", useBewertung=" + useBewertung +
                '}';
    }
}
