package com.example.owppharmacy.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WishList {
    private int id;
    private ArrayList<Medicine> medicine;

    public WishList() {

    }

    public WishList(ArrayList<Medicine> medicine) {
        this.medicine = medicine;
    }


}
