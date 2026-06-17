package com.example.myapplication.Model;

public class Categorie {
    private int id;
    private String name, type;

    // Constructeur par défaut
    public Categorie() {}

    // Constructeur avec paramètres
    public Categorie(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Categorie(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

