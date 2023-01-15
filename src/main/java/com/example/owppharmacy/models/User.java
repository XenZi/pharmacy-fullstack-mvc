package com.example.owppharmacy.models;

public class User {
    private Account account;
    private ShoppingCart shoppingCart;
    private WishList wishList;
    private ShoppingHistory shoppingHistory;
    private LoyaltyCard loyaltyCard;

    public User() {
    }

    public User(Account account, ShoppingCart shoppingCart, WishList wishList, ShoppingHistory shoppingHistory, LoyaltyCard loyaltyCard) {
        this.account = account;
        this.shoppingCart = shoppingCart;
        this.wishList = wishList;
        this.shoppingHistory = shoppingHistory;
        this.loyaltyCard = loyaltyCard;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public ShoppingHistory getShoppingHistory() {
        return shoppingHistory;
    }

    public void setShoppingHistory(ShoppingHistory shoppingHistory) {
        this.shoppingHistory = shoppingHistory;
    }

    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }
}
