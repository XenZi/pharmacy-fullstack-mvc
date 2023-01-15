package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.UserRepository;
import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findOne(int id) {
        return repository.findOne(id);
    }

    public void save(Account account) {
        repository.save(account);
    }

    public void update(Account account) {repository.update(account);}

    public void delete(int id) {repository.delete(id);}
    public void block(int id) {repository.block(id);}
    public void unblock(int id) {repository.unblock(id);}
}
