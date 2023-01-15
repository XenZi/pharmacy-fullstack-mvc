package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.MedicineManufacturer;

import java.util.List;

public interface IMedicineManufacturerRepository {
    public List<MedicineManufacturer> findAll();
    public void save(MedicineManufacturer manufacturer);
    public void update(MedicineManufacturer manufacturer);
    public void delete(int id);
    public MedicineManufacturer findOne(int id);
}
