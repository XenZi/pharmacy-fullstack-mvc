package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.DiscountRepository;
import com.example.owppharmacy.models.Discount;
import com.example.owppharmacy.models.MedicineCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DiscountService {

    @Autowired
    private MedicineCategoryService medicineCategoryService;
    @Autowired
    private DiscountRepository discountRepository;

    public void save(String startDate, String endDate, List<String> categoryID, String applyOnAll) {
        Discount discount = new Discount();
        discount.setDiscountedCategories(new ArrayList<>());
        discount.setId(UUID.randomUUID().toString());
        if (applyOnAll.equals("-1")) {
            for (String id : categoryID) {
                discount.getDiscountedCategories().add(medicineCategoryService.findOne(Integer.parseInt(id)));
            }
        }
        else {
            discount.setDiscountedCategories(((ArrayList<MedicineCategory>) (medicineCategoryService.findAll())));
        }
        discount.setStartDate(LocalDate.parse(startDate));
        discount.setEndDate((endDate.equals("-1") ? LocalDate.parse(startDate) : LocalDate.parse(endDate)));
        discountRepository.save(discount);
    }

    public Discount findActiveDiscount() {
        Discount discount = discountRepository.findCurrentActive();
        if (discount == null) {
            return null;
        }
        System.out.println(discount.getId());
        return discount;
    }
}
