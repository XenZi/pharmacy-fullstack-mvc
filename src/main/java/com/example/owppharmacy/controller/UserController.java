package com.example.owppharmacy.controller;


import com.example.owppharmacy.enums.ERole;
import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.User;
import com.example.owppharmacy.service.IUserService;
import com.example.owppharmacy.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        model.addAttribute("roles", ERole.values());
        return "user/index";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam int id, Model model) {
        model.addAttribute("user", service.findOne(id));
        return "user/edit";
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute Account account, HttpServletResponse response) throws IOException {
        service.update(account);
        response.sendRedirect("/");
    }

    @PostMapping("/delete")
    public void postDelete(@RequestParam int id, HttpServletResponse response) throws IOException {
        service.delete(id);
        response.sendRedirect("/user");

    }

    @PostMapping("/block")
    public void postBlock(@RequestParam int id, HttpServletResponse response) throws IOException {
        service.block(id);
        response.sendRedirect("/user");
    }

    @PostMapping("/unblock")
    public void postUnblock(@RequestParam int id, HttpServletResponse response) throws IOException {
        service.unblock(id);
        response.sendRedirect("/user");
    }

    @GetMapping("/search")
    public String getSearchByCriteria(
            @RequestParam(required = false, defaultValue = "-1") String username,
            @RequestParam(required = false, defaultValue = "-1") String role,
            @RequestParam(required = false, defaultValue = "-1") String sort,
            Model model) {
        model.addAttribute("users", service.findBySearchCriteria(username, role, sort));
        model.addAttribute("roles", ERole.values());
        return "user/index";
    }

}
