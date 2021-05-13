package de.eat4speed.dishAlternatives;

import java.util.List;
import java.util.Objects;

public class DishAlternativesOptions {
    private int gericht_ID;
    private String gerichtName = "";
    private List<String> kategorien;
    private List<String> excludedAllergene;
    private double maxMindestbestellwert;
    private double maxEntfernung;
    private int minBewertung;
    private boolean useName;
    private boolean useKategorien;
    private boolean useAllergene;
    private boolean useMindestbestellwert;
    private boolean useEntfernung;
    private boolean useBewertung;


    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gericht_ID) {
        this.gericht_ID = gericht_ID;
    }

    public String getGerichtName() {
        return gerichtName;
    }

    public void setGerichtName(String gerichtName) {
        this.gerichtName = gerichtName;
    }

    public List<String> getKategorien() {
        return kategorien;
    }

    public void setKategorien(List<String> kategorien) {
        this.kategorien = kategorien;
    }

    public List<String> getExcludedAllergene() {
        return excludedAllergene;
    }

    public void setExcludedAllergene(List<String> excludedAllergene) {
        this.excludedAllergene = excludedAllergene;
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

    public boolean isUseKategorien() {
        return useKategorien;
    }

    public void setUseKategorien(boolean useKategorien) {
        this.useKategorien = useKategorien;
    }

    public boolean isUseAllergene() {
        return useAllergene;
    }

    public void setUseAllergene(boolean useAllergene) {
        this.useAllergene = useAllergene;
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
        DishAlternativesOptions that = (DishAlternativesOptions) o;
        return gericht_ID == that.gericht_ID && Double.compare(that.maxMindestbestellwert, maxMindestbestellwert) == 0 && Double.compare(that.maxEntfernung, maxEntfernung) == 0 && minBewertung == that.minBewertung && useName == that.useName && useKategorien == that.useKategorien && useAllergene == that.useAllergene && useMindestbestellwert == that.useMindestbestellwert && useEntfernung == that.useEntfernung && useBewertung == that.useBewertung && Objects.equals(gerichtName, that.gerichtName) && Objects.equals(kategorien, that.kategorien) && Objects.equals(excludedAllergene, that.excludedAllergene);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_ID, gerichtName, kategorien, excludedAllergene, maxMindestbestellwert, maxEntfernung, minBewertung, useName, useKategorien, useAllergene, useMindestbestellwert, useEntfernung, useBewertung);
    }

    @Override
    public String toString() {
        return "DishAlternativesOptions{" +
                "gericht_ID=" + gericht_ID +
                ", gerichtName='" + gerichtName + '\'' +
                ", kategorien=" + kategorien +
                ", excludedAllergene=" + excludedAllergene +
                ", maxMindestbestellwert=" + maxMindestbestellwert +
                ", maxEntfernung=" + maxEntfernung +
                ", minBewertung=" + minBewertung +
                ", useName=" + useName +
                ", useKategorien=" + useKategorien +
                ", useAllergene=" + useAllergene +
                ", useMindestbestellwert=" + useMindestbestellwert +
                ", useEntfernung=" + useEntfernung +
                ", useBewertung=" + useBewertung +
                '}';
    }
}
