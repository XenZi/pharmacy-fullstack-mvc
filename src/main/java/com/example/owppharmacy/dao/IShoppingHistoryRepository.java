package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.ShoppingHistory;

public interface IShoppingHistoryRepository {
    public ShoppingHistory findOneByUserID(int id);
    public void save(int id);
}
