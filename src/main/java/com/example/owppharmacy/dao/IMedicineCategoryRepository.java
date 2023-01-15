package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.MedicineCategory;

import java.util.List;

public interface IMedicineCategoryRepository {
    public List<MedicineCategory> findAll();
    public void save(MedicineCategory category);
    public void update(MedicineCategory category);
    public void delete(int id);
    public MedicineCategory findOne(int id);
}
