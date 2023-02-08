package com.example.owppharmacy.models;

import java.time.LocalDate;

public class ShoppingHistoryItem {
    private int id;
    private Medicine medicine;
    private LocalDate date;

    public ShoppingHistoryItem(int id, Medicine medicine, LocalDate date) {
        this.id = id;
        this.medicine = medicine;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
