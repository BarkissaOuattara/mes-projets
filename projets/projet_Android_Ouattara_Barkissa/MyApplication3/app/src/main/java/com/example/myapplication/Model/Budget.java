package com.example.myapplication.Model;

public class Budget {
    private int id;
    private int userId;
    private double amountLimit;
    private String budgetName;
    private String date;
    private String month;

    // Constructeur par défaut
    public Budget() {}

    // Constructeur avec paramètres
    public Budget(int id, int userId, double amountLimit, String budgetName, String date, String month) {
        this.id = id;
        this.userId = userId;
        this.amountLimit = amountLimit;
        this.budgetName = budgetName;
        this.date = date;
        this.month = month;
    }

    public Budget(int userId, double amountLimit, String budgetName, String date, String month) {
        this.userId = userId;
        this.amountLimit = amountLimit;
        this.budgetName = budgetName;
        this.date = date;
        this.month = month;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(double amountLimit) {
        this.amountLimit = amountLimit;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
