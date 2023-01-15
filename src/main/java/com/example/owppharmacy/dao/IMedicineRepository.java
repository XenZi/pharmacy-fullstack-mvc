package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.Medicine;

import java.util.List;

public interface IMedicineRepository {
    public List<Medicine> findAll();
    public void save(Medicine medicine);
    public void update(Medicine medicine);
    public void delete(int id);
    public Medicine findOne(String id);
}
