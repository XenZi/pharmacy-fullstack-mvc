package com.example.owppharmacy.controller;


import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.User;
import com.example.owppharmacy.service.IUserService;
import com.example.owppharmacy.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("")
    public String allUsers(Model model) {
        model.addAttribute("users", service.findAll());
        return "user/index";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam int id, Model model) {
        model.addAttribute("user", service.findOne(id));
        return "user/edit";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public void postRegister(@ModelAttribute Account account) {
        service.save(account);
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute Account account, HttpServletResponse response) throws IOException {
        service.update(account);
        response.sendRedirect("/user");
    }

    @PostMapping("/delete")
    public void postDelete(int id, HttpServletResponse response) throws IOException {
        service.delete(id);
        response.sendRedirect("/user");

    }

    @PostMapping("/block")
    public void postBlock(int id) {
        service.block(id);
    }

    @PostMapping("/unblock")
    public void postUnblock(int id) {
        service.unblock(id);
    }
}
