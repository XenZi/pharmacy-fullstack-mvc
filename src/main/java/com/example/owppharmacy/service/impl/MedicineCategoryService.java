package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.MedicineCategoryRepository;
import com.example.owppharmacy.models.MedicineCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineCategoryService {
    @Autowired
    private MedicineCategoryRepository repository;

    public List<MedicineCategory> findAll() {
        repository.findAll().forEach(el -> System.out.println(el.getId()));
        return repository.findAll();
    }

    public void save(MedicineCategory category) {
        repository.save(category);
    }

    public void update(MedicineCategory category) {
        repository.update(category);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public MedicineCategory findOne(int id) { return repository.findOne(id); }
}
