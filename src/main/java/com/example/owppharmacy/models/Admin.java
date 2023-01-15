package com.example.owppharmacy.models;

public class Admin {
    private Account account;

    public Admin() {
    }

    public Admin(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
