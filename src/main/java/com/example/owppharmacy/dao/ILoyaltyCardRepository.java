package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.LoyaltyCard;

import java.util.List;

public interface ILoyaltyCardRepository {
    public List<LoyaltyCard> findAll();
    public void save(LoyaltyCard card);
    public void update(LoyaltyCard card);
    public void delete(int id);
    public LoyaltyCard findOne(int id);
}
