package com.example.owppharmacy.controller;


import com.example.owppharmacy.models.MedicineCategory;
import com.example.owppharmacy.service.impl.MedicineCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/category")
public class MedicineCategoryController {

    @Autowired
    private MedicineCategoryService service;

    @GetMapping("")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", service.findAll());
        return "category/index";
    }

    @GetMapping("/edit")
    public String editCategory(@RequestParam int id, Model model) {
        model.addAttribute("category", service.findOne(id));
        return "category/edit";
    }

    @GetMapping("/create")
    public String addCategory () {
        return  "category/create";
    }

    @PostMapping("/create")
    public void postCreate(@ModelAttribute MedicineCategory category, HttpServletResponse response) throws IOException {
        service.save(category);
        response.sendRedirect("/category");
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute MedicineCategory category, HttpServletResponse response) throws IOException {
        service.update(category);
        response.sendRedirect("/category");
    }

    @PostMapping("/delete")
    public void postDelete(int id, HttpServletResponse response) throws IOException {
        service.delete(id);
        response.sendRedirect("/category");
    }
}
