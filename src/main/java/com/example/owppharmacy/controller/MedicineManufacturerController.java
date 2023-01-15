package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.MedicineManufacturer;
import com.example.owppharmacy.service.impl.MedicineManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/manufacturer")
public class MedicineManufacturerController {

    @Autowired
    private MedicineManufacturerService service;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("manufacturers", service.findAll());
        return "manufacturer/index";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        model.addAttribute("manufacturer", service.findOne(id));
        return "manufacturer/edit";
    }

    @GetMapping("/create")
    public String create() {
        return "manufacturer/create";
    }
    @PostMapping("/create")
    public void postCreate(@ModelAttribute MedicineManufacturer manufacturer, HttpServletResponse response) throws IOException {
        service.save(manufacturer);
        response.sendRedirect("/manufacturer");
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute MedicineManufacturer manufacturer, HttpServletResponse response) throws IOException {
        service.update(manufacturer);
        response.sendRedirect("/manufacturer");
    }

    @PostMapping("/delete")
    public void postDelete(int id, HttpServletResponse response) throws IOException {
        service.delete(id);
        response.sendRedirect("/manufacturer");
    }
}
