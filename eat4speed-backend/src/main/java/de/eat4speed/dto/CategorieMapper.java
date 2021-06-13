package de.eat4speed.dto;

public class CategorieMapper {

    public String categorie;
    public int amount;

    public CategorieMapper() {
    }

    public CategorieMapper(String categorie, int amount) {
        this.categorie = categorie;
        this.amount = amount;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
