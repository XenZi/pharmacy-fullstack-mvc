package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.User;
import com.example.owppharmacy.service.impl.WishListService;
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
@RequestMapping("/wish-list")
public class WishListController {
    @Autowired
    private WishListService service;

    @GetMapping("")
    public String index(Model model, HttpSession session) {
        try {
            Account account = (Account) session.getAttribute("account");
            if (account == null) {
                model.addAttribute("errorMessage", "You must be logged in. In order to use wish list");
                return "wishlist/index";
            }
            model.addAttribute("items", service.findOneByUserID(account.getId()).getMedicine());
        } catch (NullPointerException exception) {
            model.addAttribute("errorMessage", "You must create a request in order to use wish-list");
        }
        return "wishlist/index";
    }

    @PostMapping("/delete")
    public void postDelete(@RequestParam String id, HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        service.deleteItem(user.getWishList().getId(), id);
        response.sendRedirect("/wish-list");
    }

    @GetMapping("/add")
    public void getAdd(@RequestParam String medicineName, HttpSession session, HttpServletResponse response) throws IOException {
        try {
            User user = (User) session.getAttribute("user");
            service.saveItem(user.getWishList().getId(), medicineName);
        }
        catch (Exception ex) {
            return;
        }
    }
}
