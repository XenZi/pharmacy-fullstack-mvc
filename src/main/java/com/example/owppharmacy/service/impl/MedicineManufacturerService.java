package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.MedicineManufacturerRepository;
import com.example.owppharmacy.models.MedicineManufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineManufacturerService {
    @Autowired
    private MedicineManufacturerRepository repository;
    public List<MedicineManufacturer> findAll() {
        return repository.findAll();
    }
    public void save(MedicineManufacturer manufacturer) {
        repository.save(manufacturer);
    }

    public void update(MedicineManufacturer manufacturer) {
        repository.update(manufacturer);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public MedicineManufacturer findOne(int id) {
        return repository.findOne(id);
    }
}
