package de.eat4speed.dishAlternatives;

import java.util.List;
import java.util.Objects;

public class DishAlternativesOptions {
    private int gericht_ID;
    private String gerichtName = "";
    private List<String> kategorien;
    private boolean useName;
    private boolean useKategorien;

    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gericht_ID) {
        this.gericht_ID = gericht_ID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishAlternativesOptions that = (DishAlternativesOptions) o;
        return gericht_ID == that.gericht_ID && useName == that.useName && useKategorien == that.useKategorien && Objects.equals(gerichtName, that.gerichtName) && Objects.equals(kategorien, that.kategorien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_ID, gerichtName, kategorien, useName, useKategorien);
    }

    @Override
    public String toString() {
        return "DishAlternativesOptions{" +
                "gericht_ID=" + gericht_ID +
                ", gerichtName='" + gerichtName + '\'' +
                ", kategorien=" + kategorien +
                ", useName=" + useName +
                ", useKategorien=" + useKategorien +
                '}';
    }
}
