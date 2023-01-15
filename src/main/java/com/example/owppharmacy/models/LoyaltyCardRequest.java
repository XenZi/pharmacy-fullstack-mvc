package com.example.owppharmacy.models;

import com.example.owppharmacy.enums.ELoyaltyCardStatus;

public class LoyaltyCardRequest {
    private int id;
    private User user;
    private ELoyaltyCardStatus status;

    public LoyaltyCardRequest(int id, User user, ELoyaltyCardStatus status) {
        this.id = id;
        this.user = user;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ELoyaltyCardStatus getStatus() {
        return status;
    }

    public void setStatus(ELoyaltyCardStatus status) {
        this.status = status;
    }
}
