package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.LoyaltyCardRepository;
import com.example.owppharmacy.models.LoyaltyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyCardService {
    @Autowired
    private LoyaltyCardRepository repository;

    public void save(LoyaltyCard card) {
        repository.save(card);
    }

    public void update(LoyaltyCard card) {
        repository.update(card);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public LoyaltyCard findOne(int id) {
        return repository.findOne(id);
    }
}
