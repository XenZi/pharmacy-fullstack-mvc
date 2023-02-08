package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private LoyaltyCardService loyaltyCardService;
    @Autowired
    private ShoppingHistoryService shoppingHistoryService;
    @Autowired
    private DiscountService discountService;

    public ShoppingCart getShoppingCartFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user.getShoppingCart();
    }

    public void addShoppingCartItem(String medicineID, int quantity, HttpSession session) {
        ShoppingCart shoppingCart = this.getShoppingCartFromSession(session);
        if (checkIfItemExistsInCartAlready(shoppingCart.getItems(), medicineID)) {
            ShoppingCartItem shoppingCartItem = shoppingCart.getItems().stream().filter(item -> item.getMedicine().getId().equals(medicineID)).findFirst().orElse(null);
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + quantity);
            shoppingCartItem.setItemPrice(shoppingCartItem.getMedicine().getPrice() * shoppingCartItem.getQuantity());
            shoppingCart.setTotalPrice(calculateTotalPrice(shoppingCart.getItems()));
            return;
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setMedicine(medicineService.findOne(medicineID));
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setItemPrice(shoppingCartItem.getQuantity() * shoppingCartItem.getMedicine().getPrice());
        shoppingCart.getItems().add(shoppingCartItem);
        shoppingCart.setTotalPrice(calculateTotalPrice(shoppingCart.getItems()));
    }

    public ShoppingCart updateShoppingCartItem(String medicineID, int quantity, HttpSession session) {
        ShoppingCart shoppingCart = this.getShoppingCartFromSession(session);
        ShoppingCartItem shoppingCartItem = shoppingCart.getItems().stream().filter(item -> item.getMedicine().getId().equals(medicineID)).findFirst().orElse(null);
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setItemPrice(shoppingCartItem.getMedicine().getPrice() * shoppingCartItem.getQuantity());
        shoppingCart.setTotalPrice(calculateTotalPrice(shoppingCart.getItems()));
        return shoppingCart;
    }

    public void deleteShoppingCartItem(String medicineID, HttpSession session) {
        ShoppingCart shoppingCart = this.getShoppingCartFromSession(session);
        int position = shoppingCart.getItems().indexOf(shoppingCart.getItems().stream().filter(item -> item.getMedicine().getId().equals(medicineID)).findFirst().orElse(null));
        shoppingCart.getItems().remove(position);
    }

    public String finishShoppingOrder(int loyaltyCardPoints, HttpSession session) {
        User user = (User) session.getAttribute("user");
        LoyaltyCard loyaltyCard = user.getLoyaltyCard();
        if (loyaltyCard != null || this.doesCartContainElementOnDiscount(session) == false) {
            if (loyaltyCardPoints > user.getLoyaltyCard().getPoints()) {
                return "You can't user more points than you have already on the cart!";
            }
            int totalPointsEarned = (int) user.getShoppingCart().getTotalPrice() / 500;
            loyaltyCardService.addPoints(loyaltyCard, totalPointsEarned);
            loyaltyCardService.removePoints(loyaltyCard, loyaltyCardPoints);
        }
        ArrayList<ShoppingCartItem> items = user.getShoppingCart().getItems();
        for(ShoppingCartItem item : items) {
            shoppingHistoryService.saveItem(item.getMedicine().getId(), user.getShoppingHistory().getId(), item.getQuantity());
        }
        user.getShoppingCart().setItems(new ArrayList<ShoppingCartItem>());
        return "Successfully!";
    }

    public float calculateTotalPriceWithLoyaltyCard(int points, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ShoppingCart shoppingCart = this.getShoppingCartFromSession(session);
        float totalPriceWithLoyaltyCard = (float) (shoppingCart.getTotalPrice() - (shoppingCart.getTotalPrice() * (points * 0.05)));
        return totalPriceWithLoyaltyCard;
    }

    private boolean checkIfItemExistsInCartAlready(List<ShoppingCartItem> shoppingCartItemList, String medicineID) {
        boolean doesExist = shoppingCartItemList.stream().anyMatch(item -> item.getMedicine().getId().equals(medicineID));
        return doesExist;
    }

    private float calculateTotalPrice(List<ShoppingCartItem> shoppingCartItemList) {
        float sum = shoppingCartItemList.stream()
                .map(item -> item.getItemPrice())
                .reduce((float) 0, (a, b) -> a + b);
        return sum;
    }

    public boolean doesCartContainElementOnDiscount(HttpSession session) {
        ShoppingCart shoppingCart = this.getShoppingCartFromSession(session);
        Discount discount = discountService.findActiveDiscount();
        boolean doesContain = false;
        if (discount == null) {
            return false;
        }
        for (MedicineCategory category : discount.getDiscountedCategories()) {
            for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
                if (category.getId() == shoppingCartItem.getMedicine().getCategory().getId()) {
                    doesContain = true;
                }
            }
        }
        return doesContain;
    }
}
