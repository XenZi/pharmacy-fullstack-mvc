package com.example.owppharmacy.controller;


import com.example.owppharmacy.enums.EMedicineForm;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.service.impl.CommentService;
import com.example.owppharmacy.service.impl.MedicineCategoryService;
import com.example.owppharmacy.service.impl.MedicineManufacturerService;
import com.example.owppharmacy.service.impl.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService service;
    @Autowired
    private MedicineManufacturerService manufacturerService;
    @Autowired
    private MedicineCategoryService categoryService;
    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("medicines", service.findAll());
        return "medicine/index";
    }

    @GetMapping("/view")
    public String specifiedView(@RequestParam String id, Model model) {
        model.addAttribute("medicine", service.findOne(id));
        model.addAttribute("comments", commentService.findAllForMedicineID(id));
        return "medicine/view";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("medicineForms", EMedicineForm.values());
        return "medicine/create";
    }

    @PostMapping("/create")
    public void postCreate(@ModelAttribute Medicine medicine, @RequestParam("formFile") MultipartFile file, int manufacturerID, int categoryID) throws IOException {

        service.save(medicine, manufacturerID, categoryID, file);
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute Medicine medicine, int manufacturerID, int categoryID) {
        service.update(medicine, manufacturerID, categoryID);
    }

    @PostMapping("/delete")
    public void postDelete(int id) {
        service.delete(id);
    }
}
