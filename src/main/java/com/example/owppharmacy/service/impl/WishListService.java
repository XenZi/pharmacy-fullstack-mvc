package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.WishListRepository;
import com.example.owppharmacy.models.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    @Autowired
    private WishListRepository repository;

    public WishList findOneByUserID(int userID) {
        return repository.findOneByUserID(userID);
    }
    public void deleteItem(int listID, String medicineID) {
        repository.deleteItem(listID, medicineID);
    }
    public void saveItem(int listID, String medicineID) {
        repository.saveItem(listID, medicineID);
    }
    public void saveWishList(int userID) {
        repository.saveWishList(userID);
    }
}
