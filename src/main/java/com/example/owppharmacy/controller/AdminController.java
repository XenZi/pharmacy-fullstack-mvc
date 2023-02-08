package com.example.owppharmacy.controller;


import com.example.owppharmacy.service.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("totalMedicinesLength", adminService.getTotalMedicinesLength());
        model.addAttribute("totalUserRegistered", adminService.getTotalUserRegistered());
        model.addAttribute("totalManufacturerLength", adminService.getTotalManufacturerLength());
        model.addAttribute("getTotalCategoriesLength", adminService.getTotalCategoriesLength());
        model.addAttribute("loyaltyCardRequests", adminService.getAllLoyaltyCardRequests());
        model.addAttribute("orders", adminService.getAllOrdersByStatusPendingReview());
        return "admin/index";
    }
}
