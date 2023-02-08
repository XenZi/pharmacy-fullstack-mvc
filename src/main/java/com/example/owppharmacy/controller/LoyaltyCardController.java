package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.LoyaltyCard;
import com.example.owppharmacy.service.impl.LoyaltyCardRequestService;
import com.example.owppharmacy.service.impl.LoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/loyalty-card")
public class LoyaltyCardController {
    @Autowired
    private LoyaltyCardService service;
    @Autowired
    private LoyaltyCardRequestService loyaltyCardRequestService;

    @GetMapping("")
    public String  index(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            model.addAttribute("errorMessage", "You must be logged in!");
            return "loyalty-card/index";
        }
        model.addAttribute("doesRequestExist", loyaltyCardRequestService.isRequestAlreadySent(account.getId()));
        return "loyalty-card/index";
    }

    @GetMapping("/request-new")
    public void getRequestNew(HttpSession session, HttpServletResponse response) throws IOException {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("/");
        }
        loyaltyCardRequestService.save(account.getId());
        response.sendRedirect("/loyalty-card");
    }

    @PostMapping("/create")
    public void postCreate(@ModelAttribute LoyaltyCard card, @RequestParam int user_id) {
        service.save(card, user_id);
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute LoyaltyCard card) {
        service.update(card);
    }

    @PostMapping("/delete")
    public void postDelete(int id) {
        service.delete(id);
    }

    @PostMapping("/approve")
    public void postApprove(@RequestParam int id, @RequestParam int userID, HttpServletResponse response) throws IOException {
        loyaltyCardRequestService.approveRequest(id, userID);
        response.sendRedirect("/admin");
    }
    @PostMapping("/reject")
    public void postReject(@RequestParam int id, HttpServletResponse response) throws IOException {
        loyaltyCardRequestService.rejectRequest(id);
        response.sendRedirect("/admin");
    }
}
