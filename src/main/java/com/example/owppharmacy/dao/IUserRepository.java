package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.User;

import java.util.List;

public interface IUserRepository {
    public List<User> findAll();
    public void save(Account account);
    public void update(Account account);
    public void delete(int id);

}
