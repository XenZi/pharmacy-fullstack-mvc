package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.models.LoyaltyCardRequest;
import com.example.owppharmacy.models.MedicineOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private MedicineService medicineService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineManufacturerService manufacturerService;
    @Autowired
    private MedicineCategoryService categoryService;
    @Autowired
    private LoyaltyCardRequestService loyaltyCardRequestService;
    @Autowired
    private MedicineOrderService medicineOrderService;

    public int getTotalMedicinesLength() {
        return medicineService.findAll().size();
    }

    public int getTotalUserRegistered() {
        return userService.findAll().size();
    }

    public int getTotalManufacturerLength() {
        return manufacturerService.findAll().size();
    }

    public int getTotalCategoriesLength() {
        return categoryService.findAll().size();
    }

    public List<LoyaltyCardRequest> getAllLoyaltyCardRequests() {
        return loyaltyCardRequestService.findAllByPendingRequest();
    }

    public List<MedicineOrder> getAllOrdersByStatusPendingReview() {
        return medicineOrderService.findOrdersByStatusPendingReview();
    }
}
