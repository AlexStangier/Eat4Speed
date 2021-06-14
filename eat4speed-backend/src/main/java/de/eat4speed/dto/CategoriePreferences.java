package de.eat4speed.dto;

import io.smallrye.mutiny.tuples.Tuple2;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

public class CategoriePreferences {

    public List<CategorieMapper> data;

    public CategoriePreferences() {
        this.data = new ArrayList<>();
    }

    /**
     * Appends an item to the List
     * @param categorie
     * @param amount
     */
    public void AddToList(String categorie, Integer amount) {
        this.data.add(new CategorieMapper(categorie, amount));
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
