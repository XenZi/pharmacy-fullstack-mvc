package com.example.owppharmacy.models;

public class MedicineOrderItem {
    private int id;
    private Medicine medicine;
    private String description;
    private int quantity;

    public MedicineOrderItem() {
    }

    public MedicineOrderItem(int id, Medicine medicine, String description, int quantity) {
        this.id = id;
        this.medicine = medicine;
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
