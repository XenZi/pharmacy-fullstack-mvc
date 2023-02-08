package com.example.owppharmacy.dao.impl;


import com.example.owppharmacy.enums.ERole;
import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.MedicineManufacturer;
import com.example.owppharmacy.models.ReportItem;
import com.example.owppharmacy.service.impl.MedicineManufacturerService;
import com.example.owppharmacy.service.impl.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReportItemRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MedicineManufacturerService manufacturerService;
    @Autowired
    private MedicineService medicineService;

    private class ReportItemRowMapper implements RowMapper<ReportItem> {

        @Override
        public ReportItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            Medicine medicine = medicineService.findOne(resultSet.getString(index++));
            MedicineManufacturer manufacturer = medicine.getManufacturer();
            int soldQuantity = resultSet.getInt(index++);
            ReportItem reportItem = new ReportItem();
            reportItem.setMedicine(medicine);
            reportItem.setManufacturer(manufacturer);
            reportItem.setSoldQt(soldQuantity);
            reportItem.setPricePerItem(medicine.getPrice());
            reportItem.setTotalPrice(reportItem.getPricePerItem() * reportItem.getSoldQt());
            return reportItem;
        }
    }

    public List<ReportItem> findAllByDate(String startDate, String endDate) {
        String sql = "SELECT medicine_id, quantity FROM ShoppingHistoryItem WHERE shopping_date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new ReportItemRowMapper(), startDate, endDate);
    }

    public List<ReportItem> findAllByDateAndSearchCriteria(String startDate, String endDate, String sortBy) {
        StringBuilder query = new StringBuilder("SELECT sh.medicine_id, sh.quantity, m.title, ma.title FROM ShoppingHistoryItem sh INNER JOIN Medicine m ON sh.medicine_id = m.id INNER JOIN MedicineManufacturer ma ON m.manufacturer_id = ma.id");
        StringBuilder addedQuery = new StringBuilder();
        StringBuilder orderByClause = new StringBuilder();

        if (!startDate.equals("-1") && !endDate.equals("-1")) {
            addedQuery.append(" WHERE shopping_date BETWEEN '").append(startDate + "'").append(" AND '").append(endDate + "'");
        }
        switch (sortBy) {
            case "medicineNameASC":
                query.append(" ORDER BY m.title ASC");
                break;
            case "medicineManufacturerASC":
                query.append(" ORDER BY ma.title ASC");
                break;
            case "medicineSoldASC":
                query.append(" ORDER BY quantity ASC");
                break;
            case "pricePerItemASC":
                query.append(" ORDER BY m.price ASC");
                break;
            case "medicineNameDSC":
                query.append(" ORDER BY m.title DESC");
                break;
            case "medicineManufacturerDSC":
                query.append(" ORDER BY ma.title DESC");
                break;
            case "medicineSoldDSC":
                query.append(" ORDER BY quantity DESC");
                break;
            case "pricePerItemDSC":
                query.append(" ORDER BY m.price DESC");
                break;
        }

        if (!addedQuery.isEmpty()) {
            query.append(addedQuery);
        }

        if (!orderByClause.isEmpty()) {
            query.append(" " + orderByClause);
        }
        return jdbcTemplate.query(query.toString(), new ReportItemRowMapper());
    }

}
