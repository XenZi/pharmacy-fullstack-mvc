package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.MedicineRepository;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.MedicineCategory;
import com.example.owppharmacy.models.MedicineManufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MedicineService {
        @Autowired
        private MedicineRepository repository;
        @Autowired
        private MedicineManufacturerService manufacturerService;
        @Autowired
        private MedicineCategoryService categoryService;
        @Autowired
        private FileStorageService fileStorageService;

        public List<Medicine> findAll() {
                return repository.findAll();
        }
        public void save(Medicine medicine, int manufacturerID, int categoryID, MultipartFile file) throws IOException {
                MedicineManufacturer manufacturer = manufacturerService.findOne(manufacturerID);
                MedicineCategory category = categoryService.findOne(categoryID);
                fileStorageService.save(file);
                medicine.setPicture(file.getOriginalFilename());
                medicine.setCategory(category);
                medicine.setManufacturer(manufacturer);
                medicine.setQuantity(0);
                medicine.setAvgRating(0);
                repository.save(medicine);
        }

        public void update(Medicine medicine, int manufacturerID, int categoryID) {
                MedicineManufacturer manufacturer = manufacturerService.findOne(manufacturerID);
                MedicineCategory category = categoryService.findOne(categoryID);
                medicine.setManufacturer(manufacturer);
                medicine.setCategory(category);
                repository.update(medicine);
        }

        public void delete(int id) {
                repository.delete(id);
        }

        public Medicine findOne(String id) {
                return repository.findOne(id);
        }
}
