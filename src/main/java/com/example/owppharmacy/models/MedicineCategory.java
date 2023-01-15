package com.example.owppharmacy.models;

public class MedicineCategory {
    private int id;
    private String title;
    private String purpose;
    private String description;

    public MedicineCategory() {

    }

    public MedicineCategory(int id, String title, String purpose, String description) {
        this.id = id;
        this.title = title;
        this.purpose = purpose;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}