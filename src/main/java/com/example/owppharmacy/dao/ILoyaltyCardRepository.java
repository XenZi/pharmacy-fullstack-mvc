package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.LoyaltyCard;

import java.util.List;

public interface ILoyaltyCardRepository {
    public List<LoyaltyCard> findAll();
    public void save(LoyaltyCard card, int userID);
    public void update(LoyaltyCard card);
    public void delete(int id);
    public LoyaltyCard findOne(int id);
    public void removePoints(LoyaltyCard card, int points);
    public void addPoints(LoyaltyCard card, int points);
}
