package com.example.owppharmacy.controller;


import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.service.impl.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shopping-history")
public class ShoppingHistoryController {

    @Autowired
    private ShoppingHistoryService service;

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            model.addAttribute("errorMessage", "You must be logged in!");
            return "shopping-history/index";
        }
        model.addAttribute("historyItems", service.findOneByUserID(account.getId()).getHistory());
        return "shopping-history/index";
    }

}
