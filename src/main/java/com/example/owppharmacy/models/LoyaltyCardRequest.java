package com.example.owppharmacy.models;

import com.example.owppharmacy.enums.ELoyaltyCardStatus;

public class LoyaltyCardRequest {
    private int id;
    private Account account;
    private ELoyaltyCardStatus status;

    public LoyaltyCardRequest() {}
    public LoyaltyCardRequest(int id, Account account, ELoyaltyCardStatus status) {
        this.id = id;
        this.account = account;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ELoyaltyCardStatus getStatus() {
        return status;
    }

    public void setStatus(ELoyaltyCardStatus status) {
        this.status = status;
    }
}
