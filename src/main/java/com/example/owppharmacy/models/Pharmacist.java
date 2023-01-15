package com.example.owppharmacy.models;

public class Pharmacist {
    private Account account;

    public Pharmacist() {
    }

    public Pharmacist(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
