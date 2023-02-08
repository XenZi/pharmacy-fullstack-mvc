package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.UserRepository;
import com.example.owppharmacy.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private ShoppingHistoryService shoppingHistoryService;
    @Autowired
    private LoyaltyCardService loyaltyCardService;


    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findOne(int id) {
        return repository.findOne(id);
    }

    public Account findOne(String username, String password) {
        return repository.findOne(username, password);
    }

    public boolean userLogin(String username, String password, HttpSession session) {
        Account account = this.findOne(username, password);
        if (account == null || account.isBlocked()) {
            return false;
        }
        session.setAttribute("account", account);
        switch (account.getUserRole()) {
            case USER:
                User user = new User();
                WishList wishList = wishListService.findOneByUserID(account.getId());
                ShoppingHistory shoppingHistory = shoppingHistoryService.findOneByUserID(account.getId());
                LoyaltyCard loyaltyCard = loyaltyCardService.findOne(account.getId());
                ShoppingCart shoppingCart = new ShoppingCart();
                user.setShoppingCart(shoppingCart);
                user.setLoyaltyCard(loyaltyCard);
                user.setShoppingHistory(shoppingHistory);
                user.setAccount(account);
                user.setWishList(wishList);
                session.setAttribute("user", user);
                break;
            case ADMIN:
                Admin admin = new Admin();
                admin.setAccount(account);
                session.setAttribute("admin", admin);
                break;
            case PHARMACIST:
                Pharmacist pharmacist = new Pharmacist();
                pharmacist.setAccount(account);
                session.setAttribute("pharmacist", pharmacist);
                break;
        }
        return true;
    }

    public void save(Account account) {
        repository.save(account);
    }

    public void update(Account account) {repository.update(account);}

    public void delete(int id) {repository.delete(id);}
    public void block(int id) {repository.block(id);}
    public void unblock(int id) {repository.unblock(id);}

    public List<Account> findBySearchCriteria(String username, String role, String orderBy) {
        return repository.findAllBySearchCriteria(username, role, orderBy);
    }
}
