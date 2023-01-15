package com.example.owppharmacy.models;

public class MedicineManufacturer {
    private int id;
    private String title;
    private String hqLocation;

    public MedicineManufacturer() {}
    public MedicineManufacturer(int id, String title, String hqLocation) {
        this.id = id;
        this.title = title;
        this.hqLocation = hqLocation;
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

    public String getHqLocation() {
        return hqLocation;
    }

    public void setHqLocation(String hqLocation) {
        this.hqLocation = hqLocation;
    }
}
