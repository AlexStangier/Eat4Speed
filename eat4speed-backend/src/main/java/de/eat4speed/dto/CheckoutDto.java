package de.eat4speed.dto;

public class CheckoutDto {
    public int orderId;

    public CheckoutDto() {
    }

    public CheckoutDto(int id) {
        this.orderId = id;
    }

    public int getId() {
        return orderId;
    }

    public void setId(int id) {
        this.orderId = id;
    }
}

