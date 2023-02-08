package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.ShoppingHistoryRepository;
import com.example.owppharmacy.models.ShoppingHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ShoppingHistoryService {
    @Autowired
    private ShoppingHistoryRepository repository;

    public ShoppingHistory findOneByUserID(int id) {
        return repository.findOneByUserID(id);
    }

    public boolean didUserBuyMedicineByID(int userID, String medicineID) {
        ShoppingHistory shoppingHistory = this.findOneByUserID(userID);
        boolean doesExist = shoppingHistory.getHistory().stream().anyMatch(x -> x.getMedicine().getId().equals(medicineID));
        return doesExist;
    }

    public void save(int id) {repository.save(id);}

    public void saveItem(String medicineID, int userShoppingHistoryID, int quantity) {
        LocalDate localDate = LocalDate.now();
        repository.saveItem(medicineID, userShoppingHistoryID, localDate, quantity);
    }
}
