package com.example.owppharmacy.controller;

import com.example.owppharmacy.service.impl.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/pharmacist")
public class PharmacistController {

    @Autowired
    private PharmacistService service;

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        model.addAttribute("orders", service.findOrdersByPharmacistID(session));
        model.addAttribute("medicines", service.findAllMedicinesForApprovment());
        return "pharmacist/index";
    }

}
