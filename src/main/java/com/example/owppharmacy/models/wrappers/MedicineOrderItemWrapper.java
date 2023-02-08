package com.example.owppharmacy.models.wrappers;

public class MedicineOrderItemWrapper {
    private String medicineID;
    private int quantity;
    private String description;

    public MedicineOrderItemWrapper() {
    }

    public MedicineOrderItemWrapper(String medicineID, int quantity, String description) {
        this.medicineID = medicineID;
        this.quantity = quantity;
        this.description = description;
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
}
