package com.example.owppharmacy.service.impl;


import com.example.owppharmacy.dao.impl.ReportItemRepository;
import com.example.owppharmacy.models.ReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportItemRepository repository;

    public List<ReportItem> findByDates(String startDate, String endDate) {
        return repository.findAllByDate(startDate, endDate);
    }

    public List<ReportItem> findAllByDateAndSearchCriteria(String startDate, String endDate, String sortBy) {
        return repository.findAllByDateAndSearchCriteria(startDate, endDate, sortBy);
    }

    public int totalSoldQuantity(String startDate, String endDate) {
        List<ReportItem> list = repository.findAllByDate(startDate, endDate);
        int totalSold = list.stream()
                .map(item -> item.getSoldQt())
                .reduce( 0, (a, b) -> a + b);
        return totalSold;
    }

    public float totalCashEarned(String startDate, String endDate) {
        List<ReportItem> list = repository.findAllByDate(startDate, endDate);
        float totalSold = list.stream()
                .map(item -> item.getTotalPrice())
                .reduce( (float) 0, (a, b) -> a + b);
        return totalSold;
    }

}
