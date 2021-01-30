package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Restaurant_Zeiten", schema = "eatforspeed", catalog = "")
public class RestaurantZeitenEntity {
    private Long id;
    private int restaurantId;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Restaurant_ID")
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantZeitenEntity that = (RestaurantZeitenEntity) o;

        if (restaurantId != that.restaurantId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return restaurantId;
    }
}
