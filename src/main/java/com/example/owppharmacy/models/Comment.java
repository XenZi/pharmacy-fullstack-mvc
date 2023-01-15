package com.example.owppharmacy.models;

import java.time.LocalDate;

public class Comment {
    private int id;
    private Account user;
    private String description;
    private LocalDate dateSubmission;
    private Medicine medicine;
    private boolean isAnonymous;
    private float rating;

    public Comment() {}

    public Comment(int id, Account user, String description, LocalDate dateSubmission, Medicine medicine, boolean isAnonymous) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.dateSubmission = dateSubmission;
        this.medicine = medicine;
        this.isAnonymous = isAnonymous;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateSubmission() {
        return dateSubmission;
    }

    public void setDateSubmission(LocalDate dateSubmission) {
        this.dateSubmission = dateSubmission;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
