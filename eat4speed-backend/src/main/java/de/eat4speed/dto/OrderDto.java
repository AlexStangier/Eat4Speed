package de.eat4speed.dto;

public class OrderDto {
    public int[] items;
    public int customerId;
    public long timestamp;

    public OrderDto() {
    }

    public OrderDto(int[] items, int customerId) {
        this.items = items;
        this.customerId = customerId;
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
