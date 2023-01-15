package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.LoyaltyCard;
import com.example.owppharmacy.service.impl.LoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loyalty-card")
public class LoyaltyCardController {
    @Autowired
    private LoyaltyCardService service;

    @PostMapping("/create")
    public void postCreate(@ModelAttribute LoyaltyCard card) {
        service.save(card);
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute LoyaltyCard card) {
        service.update(card);
    }

    @PostMapping("/delete")
    public void postDelete(int id) {
        service.delete(id);
    }
}
