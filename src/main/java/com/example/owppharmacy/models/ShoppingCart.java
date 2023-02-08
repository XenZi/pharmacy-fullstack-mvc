package com.example.owppharmacy.models;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<ShoppingCartItem> items;
    private float totalPrice;

    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.totalPrice = 0;
    }

    public ShoppingCart(ArrayList<ShoppingCartItem> items, float totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
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

