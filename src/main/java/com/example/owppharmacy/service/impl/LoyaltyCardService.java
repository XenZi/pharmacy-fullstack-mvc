package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.LoyaltyCardRepository;
import com.example.owppharmacy.models.LoyaltyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyCardService {
    @Autowired
    private LoyaltyCardRepository repository;

    public LoyaltyCard findOne(int id) {
        return repository.findOne(id);
    }
    public void save(LoyaltyCard card, int userID) {
        repository.save(card, userID);
    }
    public void update(LoyaltyCard card) {
        repository.update(card);
    }
    public void delete(int id) {
        repository.delete(id);
    }
    public void removePoints(LoyaltyCard card, int points) {
        card.setPoints(card.getPoints() - points);
        repository.removePoints(card, points);
    }
    public void addPoints(LoyaltyCard card, int points) {
        card.setPoints(card.getPoints() + points);
        repository.addPoints(card, points);
    }
}
