package de.eat4speed.dto;

import java.text.DecimalFormat;

public class PaymentDto {

    private static DecimalFormat df = new DecimalFormat("0.00");

    public String amount;
    public String amountTax;
    public double shipping;
    public String state;


    public PaymentDto() {
    }

    public PaymentDto(double amount, String state) {
        this.amount = df.format(amount);
        this.state = state;
        this.amountTax = df.format((amount * 0.93) * 0.07);
        this.shipping = 2;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(double amountTax) {
        this.amountTax = String.valueOf(amountTax);
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = String.valueOf(amount);
    }
}
