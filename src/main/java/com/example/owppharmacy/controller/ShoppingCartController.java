package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.ShoppingCart;
import com.example.owppharmacy.service.impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService service;


    @GetMapping("")
    public String index(Model model, HttpSession session) {
        model.addAttribute("activeDiscount", service.doesCartContainElementOnDiscount(session));
        model.addAttribute("shoppingCart", service.getShoppingCartFromSession(session));
        model.addAttribute("items", service.getShoppingCartFromSession(session).getItems());
        return "shopping-cart/index";
    }

    @GetMapping("/calculate-price")
    @ResponseBody
    public float getCalculateTotalPriceWithLoyaltyCard(@RequestParam int points, HttpSession session) {
        return service.calculateTotalPriceWithLoyaltyCard(points, session);
    }

    @PostMapping("/add")
    public void postAddItemToCart(@RequestParam String medicineID, @RequestParam int quantity, HttpSession session) {
        service.addShoppingCartItem(medicineID, quantity, session);
    }

    @PostMapping("/update")
    @ResponseBody
    public float postUpdateItemToCart(@RequestParam String medicineID, @RequestParam int quantity, HttpSession session) {
        ShoppingCart shoppingCart = service.updateShoppingCartItem(medicineID, quantity, session);
        return shoppingCart.getTotalPrice();
    }

    @PostMapping("/delete")
    public void postDelete(@RequestParam String medicineID, HttpSession session, HttpServletResponse response) throws IOException {
        service.deleteShoppingCartItem(medicineID, session);
        response.sendRedirect("/cart");
    }

    @PostMapping("/buy")
    @ResponseBody
    public String postBuy(@RequestParam int loyaltyCardPoints, HttpSession session) {
        return service.finishShoppingOrder(loyaltyCardPoints, session);
    }
}
