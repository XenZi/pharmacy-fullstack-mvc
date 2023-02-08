package com.example.owppharmacy.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Discount {
    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<MedicineCategory> discountedCategories;

    public Discount() {}

    public Discount(String id, LocalDate startDate, LocalDate endDate, ArrayList<MedicineCategory> discountedCategories) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountedCategories = discountedCategories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ArrayList<MedicineCategory> getDiscountedCategories() {
        return discountedCategories;
    }

    public void setDiscountedCategories(ArrayList<MedicineCategory> discountedCategories) {
        this.discountedCategories = discountedCategories;
    }
}
