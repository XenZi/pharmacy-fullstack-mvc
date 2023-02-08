package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.WishList;

public interface IWishListRepository {
    public WishList findOne(int id);
    public WishList findOneByUserID(int id);
    public void deleteItem(int listID, String medicineID);
    public void saveItem(int listID, String medicineID);
    public void saveWishList(int userID);
}
