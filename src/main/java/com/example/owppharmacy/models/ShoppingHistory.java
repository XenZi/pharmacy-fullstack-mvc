package com.example.owppharmacy.models;

import java.util.ArrayList;

public class ShoppingHistory {
    private User user;
    private ArrayList<Medicine> boughtMedicineHistory;

    public ShoppingHistory() {}

    public ShoppingHistory(User user, ArrayList<Medicine> boughtMedicineHistory) {
        this.user = user;
        this.boughtMedicineHistory = boughtMedicineHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Medicine> getBoughtMedicineHistory() {
        return boughtMedicineHistory;
    }

    public void setBoughtMedicineHistory(ArrayList<Medicine> boughtMedicineHistory) {
        this.boughtMedicineHistory = boughtMedicineHistory;
    }
}
