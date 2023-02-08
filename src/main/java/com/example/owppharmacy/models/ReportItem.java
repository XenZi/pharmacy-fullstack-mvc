package com.example.owppharmacy.models;

public class ReportItem {
    private Medicine medicine;
    private MedicineManufacturer manufacturer;
    private float pricePerItem;
    private int soldQt;
    private float totalPrice;

    public ReportItem() {
    }

    public ReportItem(Medicine medicine, MedicineManufacturer manufacturer, float pricePerItem, int soldQt, float totalPrice) {
        this.medicine = medicine;
        this.manufacturer = manufacturer;
        this.pricePerItem = pricePerItem;
        this.soldQt = soldQt;
        this.totalPrice = totalPrice;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public MedicineManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(MedicineManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(float pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public int getSoldQt() {
        return soldQt;
    }

    public void setSoldQt(int soldQt) {
        this.soldQt = soldQt;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
