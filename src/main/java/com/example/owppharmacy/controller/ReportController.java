package com.example.owppharmacy.controller;


import com.example.owppharmacy.enums.ERole;
import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.service.impl.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService service;

    @GetMapping("")
    public String index(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        Account account = (Account) session.getAttribute("account");
        if (account.getUserRole() == ERole.PHARMACIST || account.getUserRole() == ERole.USER) {
            response.sendRedirect("/");
        }
        return "report/index";
    }

    @GetMapping("/search")
    public String index(
            @RequestParam (required = false, defaultValue = "-1") String startDate, @RequestParam (required = false, defaultValue = "-1") String endDate,
            @RequestParam(required = false, defaultValue = "-1") String sortBy,
            Model model, HttpSession session, HttpServletResponse response) throws IOException {
        Account account = (Account) session.getAttribute("account");
        if (account.getUserRole() == ERole.PHARMACIST || account.getUserRole() == ERole.USER) {
            response.sendRedirect("/");
        }
        model.addAttribute("items", service.findAllByDateAndSearchCriteria(startDate, endDate, sortBy));
        model.addAttribute("totalSold", service.totalSoldQuantity(startDate, endDate));
        model.addAttribute("totalEarned", service.totalCashEarned(startDate, endDate));
        return "report/index";
    }
}
