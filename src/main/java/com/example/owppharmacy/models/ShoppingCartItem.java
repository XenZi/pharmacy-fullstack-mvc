package com.example.owppharmacy.models;

public class ShoppingCartItem {
    private Medicine medicine;
    private int quantity;
    private float itemPrice;
    public ShoppingCartItem() {}

    public ShoppingCartItem(int id, Medicine medicine, int quantity, float itemPrice) {

        this.medicine = medicine;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }
}
