package com.example.owppharmacy.models.wrappers;

public class MedicineOrderItemWithIDWrapper {
    private int id;
    private String medicineID;
    private int quantity;
    private String description;
    private String orderID;

    public MedicineOrderItemWithIDWrapper() {
    }

    public MedicineOrderItemWithIDWrapper(int id, String medicineID, int quantity, String description, String orderID) {
        this.id = id;
        this.medicineID = medicineID;
        this.quantity = quantity;
        this.description = description;
        this.orderID = orderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
