package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.LoyaltyCardRequestRepository;
import com.example.owppharmacy.models.LoyaltyCard;
import com.example.owppharmacy.models.LoyaltyCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyCardRequestService {
    @Autowired
    private LoyaltyCardRequestRepository repository;
    @Autowired
    private LoyaltyCardService loyaltyCardService;

    public void save(int id) {
        repository.save(id);
    }

    public boolean isRequestAlreadySent(int id) {
        LoyaltyCardRequest loyaltyCardRequest = repository.findOne(id);
        if (loyaltyCardRequest == null) {
            return false;
        }
        return true;
    }

    public void approveRequest(int id, int userID) {
        repository.approveRequest(id, userID);
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setDiscount(5);
        loyaltyCard.setPoints(10);
        loyaltyCardService.save(loyaltyCard, userID);
    }

    public void rejectRequest(int id) {
        repository.delete(id);
    }

    public List<LoyaltyCardRequest> findAll() {
        return repository.findAll();
    }
    public List<LoyaltyCardRequest> findAllByPendingRequest() {
        return repository.findAllByPendingRequest();
    };
}
