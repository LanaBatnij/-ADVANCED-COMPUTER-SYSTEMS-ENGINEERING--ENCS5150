package com.example.project_1200308_1201738.Models;

import java.io.Serializable;

public class Pizza implements Serializable {
    private int Id;
    private String name;
    private String description;
    private String category;
    private CharSequence sizes;

    public Pizza() {
    }

    public Pizza(int id, String name, String description, String category) {
        this.Id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Pizza(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

}
