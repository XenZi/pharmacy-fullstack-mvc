package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.MedicineOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PharmacistService {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private MedicineOrderService medicineOrderService;

    public List<Medicine> findAllMedicinesForApprovment() {
        return medicineService.findAllByUnapprovedStatus();
    }

    public List<MedicineOrder> findOrdersByPharmacistID(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        return medicineOrderService.findOrdersByPharmacistID(account.getId());
    }
}
