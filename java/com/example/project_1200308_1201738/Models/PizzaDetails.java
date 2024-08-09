package com.example.project_1200308_1201738.Models;

public class PizzaDetails extends Pizza {
    private int id;
    private String[] sizes;
    private double[] prices;

    public PizzaDetails(int id, String name, String description, String category, String[] sizes, double[] prices) {
        super(id, name, description, category);
        this.sizes = sizes;
        this.prices = prices;
    }

    public PizzaDetails(String name, String description, String category, String[] sizes, double[] prices) {
        super( name, description, category);
        this.sizes = sizes;
        this.prices = prices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getSizes() {
        return sizes;
    }

    public double[] getPrices() {
        return prices;
    }
}
