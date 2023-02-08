package com.example.owppharmacy.models;

import java.util.ArrayList;

public class ShoppingHistory {
    private int id;
    private ArrayList<ShoppingHistoryItem> history;

    public ShoppingHistory() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ShoppingHistoryItem> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<ShoppingHistoryItem> history) {
        this.history = history;
    }
}
