package com.example.owppharmacy.controller;


import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService service;

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @GetMapping("/login")
    public String  getLogin() { return "/login"; }

    @PostMapping("/register")
    public void postRegister(@ModelAttribute Account account, HttpServletResponse response) throws IOException {
        service.save(account);
        response.sendRedirect("/user");
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model, HttpServletResponse response) throws IOException {
        boolean isLoggedIn = service.userLogin(username, password, session);
        if (!isLoggedIn) {
            model.addAttribute("errorMessage", "You have entered wrong credentials");
            return "login";
        }
        response.sendRedirect("/");
        return "index";
    }

    @GetMapping("/logout")
    public void getLogout(HttpSession session, HttpServletResponse response) throws IOException {
        session.removeAttribute("account");
        session.removeAttribute("user");
        session.removeAttribute("pharmacist");
        session.removeAttribute("admin");
        response.sendRedirect("/");
    }
}
