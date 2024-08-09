package com.example.project_1200308_1201738.Models;

import java.io.Serializable;

public class Order implements Serializable {
    private PizzaDetails pizza;
    private String dateTime;

    public Order(PizzaDetails pizza, String dateTime) {
        this.pizza = pizza;
        this.dateTime = dateTime;
    }

    public PizzaDetails getPizza() {
        return pizza;
    }

    public void setPizza(PizzaDetails pizza) {
        this.pizza = pizza;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "pizza=" + pizza +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
