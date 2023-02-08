package com.example.owppharmacy.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WishList {
    private int id;
    private ArrayList<Medicine> medicine;

    public WishList() {
        this.medicine = new ArrayList<Medicine>();
    }

    public WishList(ArrayList<Medicine> medicine) {
        this.medicine = medicine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(ArrayList<Medicine> medicine) {
        this.medicine = medicine;
    }

}
