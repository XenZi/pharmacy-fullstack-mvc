package com.example.owppharmacy.models;

import java.util.ArrayList;

public class ShoppingCart {
    private int id;
    private ArrayList<ShoppingCartItem> items;
    private float totalPrice;

    public ShoppingCart() {}

    public ShoppingCart(int id, ArrayList<ShoppingCartItem> items, float totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShoppingCartItem> items) {
        this.items = items;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

}

