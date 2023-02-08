package com.example.owppharmacy.controller;


import com.example.owppharmacy.enums.EMedicineForm;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.service.impl.MedicineCategoryService;
import com.example.owppharmacy.service.impl.MedicineManufacturerService;
import com.example.owppharmacy.service.impl.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping()
public class IndexController {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private MedicineCategoryService medicineCategoryService;
    @Autowired
    private MedicineManufacturerService manufacturerService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("medicines", medicineService.findAll());
        model.addAttribute("categories", medicineCategoryService.findAll());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("medicineForms", EMedicineForm.values());
        return "index";
    }

    @GetMapping(value = "/search", produces = "application/json")
    public String  searchIndex(Model model,
                               @RequestParam(required = false, defaultValue = "-1") String medicineName,
                               @RequestParam(required = false, defaultValue = "-1") String categoryID,
                               @RequestParam(required = false, defaultValue = "-1") String priceFrom,
                               @RequestParam(required = false, defaultValue = "-1") String priceTo,
                               @RequestParam(required = false, defaultValue = "-1") String manufacturerID,
                               @RequestParam(required = false, defaultValue = "-1") String contraindications,
                               @RequestParam(required = false, defaultValue = "-1") String description,
                               @RequestParam(required = false, defaultValue = "-1") String medicineForm,
                               @RequestParam(required = false, defaultValue = "-1") String avgRating,
                               @RequestParam(required = false, defaultValue = "-1") String medicineID,
                               @RequestParam(required = false, defaultValue = "-1") String sortBy) {
        model.addAttribute("medicines", medicineService.findAllBySearchCriteria(medicineName, categoryID, priceFrom, priceTo, manufacturerID, contraindications, description, medicineForm, avgRating, medicineID, sortBy));
        model.addAttribute("categories", medicineCategoryService.findAll());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("medicineForms", EMedicineForm.values());
        return "index";
    }

}
