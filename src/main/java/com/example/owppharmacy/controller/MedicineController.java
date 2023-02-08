package com.example.owppharmacy.controller;


import com.example.owppharmacy.enums.EMedicineForm;
import com.example.owppharmacy.enums.ERole;
import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.User;
import com.example.owppharmacy.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @GetMapping("")
    public String index(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        Account account = (Account) session.getAttribute("account");
        if (account.getUserRole() == ERole.USER) {
            response.sendRedirect("/");
        }
        model.addAttribute("medicines", service.findAll());
        return "medicine/index";
    }

    @GetMapping("/view")
    public String specifiedView(@RequestParam String id, Model model, HttpSession session) {
        model.addAttribute("medicine", service.findOne(id));
        model.addAttribute("comments", commentService.findAllForMedicineID(id));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("didUserBuyThisMedicine", shoppingHistoryService.didUserBuyMedicineByID(user.getAccount().getId(), id));
        }
        return "medicine/view";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("medicineForms", EMedicineForm.values());
        return "medicine/create";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam String id) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("medicineForms", EMedicineForm.values());
        model.addAttribute("medicine", service.findOne(id));
        return "medicine/edit";
    }

    @PostMapping("/create")
    public void postCreate(@ModelAttribute Medicine medicine, @RequestParam("formFile") MultipartFile file, int manufacturerID, int categoryID, HttpServletResponse response) throws IOException {
        service.save(medicine, manufacturerID, categoryID, file);
        response.sendRedirect("/medicine");
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute Medicine medicine, int manufacturerID, int categoryID, HttpServletResponse response) throws IOException {
        service.update(medicine, manufacturerID, categoryID);
        response.sendRedirect("/medicine");
    }

    @PostMapping("/delete")
    public void postDelete(String id, HttpServletResponse response) throws IOException {
        service.delete(id);
        response.sendRedirect("/medicine");
    }

    @PostMapping("/approve")
    public void postApprove(@RequestParam String id, HttpServletResponse response) throws IOException {
        service.approveMedicine(id);
        response.sendRedirect("/pharmacist");
    }
}
