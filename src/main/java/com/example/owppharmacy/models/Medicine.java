package com.example.owppharmacy.models;

import com.example.owppharmacy.enums.EMedicineForm;

public class Medicine {
    private String id;
    private String title;
    private String description;
    private String contraindications;
    private EMedicineForm medicineForm;
    private float avgRating;
    private String picture;
    private int quantity;
    private float price;
    private MedicineManufacturer manufacturer;
    private MedicineCategory category;
    private boolean approved;

    public Medicine() {

    }

    public Medicine(String id, String title, String description, String contraindications, EMedicineForm medicineForm, float avgRating, String picture, int quantity, float price, MedicineManufacturer manufacturer, MedicineCategory category, boolean approved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.contraindications = contraindications;
        this.medicineForm = medicineForm;
        this.avgRating = avgRating;
        this.picture = picture;
        this.quantity = quantity;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
        this.approved = approved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public EMedicineForm getMedicineForm() {
        return medicineForm;
    }

    public void setMedicineForm(EMedicineForm medicineForm) {
        this.medicineForm = medicineForm;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MedicineManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(MedicineManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public MedicineCategory getCategory() {
        return category;
    }

    public void setCategory(MedicineCategory category) {
        this.category = category;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
