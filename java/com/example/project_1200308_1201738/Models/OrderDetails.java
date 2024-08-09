package com.example.project_1200308_1201738.Models;

public class OrderDetails {

    private int id;
    private int idpizza;
    private String pizzaName;
    private String size;
    private double price;
    private String dateTime;

    public OrderDetails(int id, int idpizza,String pizzaName, String size, double price, String dateTime) {
        this.id = id;
        this.idpizza = idpizza;
        this.pizzaName = pizzaName;
        this.size = size;
        this.price = price;
        this.dateTime = dateTime;
    }

    public OrderDetails(int idpizza, String size, double price, String dateTime, String pizzaName) {
        this.idpizza = idpizza;
        this.pizzaName = pizzaName;
        this.size = size;
        this.price = price;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getDateTime() {
        return dateTime;
    }
}
