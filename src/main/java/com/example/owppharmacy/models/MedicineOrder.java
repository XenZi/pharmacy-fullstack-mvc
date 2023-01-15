package com.example.owppharmacy.models;

import com.example.owppharmacy.enums.EOrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MedicineOrder {
    private int id;
    private LocalDateTime creationDate;
    private Pharmacist pharmacist;
    private ArrayList<MedicineOrderItem> orderedItems;
    private EOrderStatus status;
    private String orderFeedback;

    public MedicineOrder() {
    }

    public MedicineOrder(int id, LocalDateTime creationDate, Pharmacist pharmacist, ArrayList<MedicineOrderItem> orderedItems, EOrderStatus status, String orderFeedback) {
        this.id = id;
        this.creationDate = creationDate;
        this.pharmacist = pharmacist;
        this.orderedItems = orderedItems;
        this.status = status;
        this.orderFeedback = orderFeedback;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public ArrayList<MedicineOrderItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(ArrayList<MedicineOrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public EOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EOrderStatus status) {
        this.status = status;
    }

    public String getOrderFeedback() {
        return orderFeedback;
    }

    public void setOrderFeedback(String orderFeedback) {
        this.orderFeedback = orderFeedback;
    }
}
