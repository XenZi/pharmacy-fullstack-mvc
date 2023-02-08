package com.example.owppharmacy.controller;


import com.example.owppharmacy.service.impl.DiscountService;
import com.example.owppharmacy.service.impl.MedicineCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService service;
    @Autowired
    private MedicineCategoryService medicineCategoryService;

    @GetMapping("/create")
    public String getIndex(Model model) {
        model.addAttribute("categories", medicineCategoryService.findAll());
        service.findActiveDiscount();
        return "discount/create";
    }

    @PostMapping("/create")
    public void postIndex(@RequestParam String startDate, @RequestParam(required = false, defaultValue = "-1") String endDate, @RequestParam(required = false, defaultValue = "-1") List<String> categoryID, @RequestParam(required = false, defaultValue = "-1") String applyOnAll, HttpServletResponse response) throws IOException {
        service.save(startDate, endDate, categoryID, applyOnAll);
        response.sendRedirect("/");
    }
}
