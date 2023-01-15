package com.example.owppharmacy.controller;


import com.example.owppharmacy.service.impl.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class IndexController {
    @Autowired
    private MedicineService medicineService;
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("medicines", medicineService.findAll());
        return "index";
    }
}
