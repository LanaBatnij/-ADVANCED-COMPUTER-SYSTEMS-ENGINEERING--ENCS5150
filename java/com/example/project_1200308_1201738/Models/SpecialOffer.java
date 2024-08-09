package com.example.project_1200308_1201738.Models;

public class SpecialOffer {
    private String pizzaTypes;
    private String sizes;
    private String offerPeriod;
    private double totalPrice;

    public SpecialOffer(String pizzaTypes, String sizes, String offerPeriod, double totalPrice) {
        this.pizzaTypes = pizzaTypes;
        this.sizes = sizes;
        this.offerPeriod = offerPeriod;
        this.totalPrice = totalPrice;
    }

    public String getPizzaTypes() {
        return pizzaTypes;
    }

    public void setPizzaTypes(String pizzaTypes) {
        this.pizzaTypes = pizzaTypes;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getOfferPeriod() {
        return offerPeriod;
    }

    public void setOfferPeriod(String offerPeriod) {
        this.offerPeriod = offerPeriod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
