package com.example.owppharmacy.dao.impl;


import com.example.owppharmacy.models.Discount;
import com.example.owppharmacy.models.LoyaltyCard;
import com.example.owppharmacy.models.MedicineCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DiscountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MedicineCategoryRepository medicineCategoryRepository;

    private class DiscountRowMapper implements RowMapper<Discount> {

        @Override
        public Discount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            String id = resultSet.getString(index++);
            LocalDate startDate = resultSet.getDate(index++).toLocalDate();
            LocalDate endDate = resultSet.getDate(index++).toLocalDate();
            Discount discount = new Discount();
            discount.setId(id);
            discount.setStartDate(startDate);
            discount.setEndDate(endDate);
            return discount;
        }
    }

    private class DiscountItemRowMapper implements RowMapper<MedicineCategory> {
        @Override
        public MedicineCategory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            MedicineCategory category = medicineCategoryRepository.findOne(id);
            return category;
        }
    }

    public List<Discount> findAll() {
        String sql = "SELECT id, start_date, end_date FROM Discount";
        return jdbcTemplate.query(sql, new DiscountRowMapper());
    }

    public List<MedicineCategory> findAllDiscountedCategoriesByDiscountID(String id) {
        String sql = "SELECT category_id FROM onDiscount WHERE discount_id = ?";
        return jdbcTemplate.query(sql, new DiscountItemRowMapper(), id);
    }

    public Discount findCurrentActive() {
        try {
            String sql = "SELECT id, start_date, end_date FROM Discount WHERE start_date < ? and end_date >= ? or start_date = ?";
            Discount discount = jdbcTemplate.queryForObject(sql, new DiscountRowMapper(), LocalDate.now(), LocalDate.now(), LocalDate.now());
            discount.setDiscountedCategories((ArrayList<MedicineCategory>) this.findAllDiscountedCategoriesByDiscountID(discount.getId()));
            return discount;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Discount discount) {
        String sql = "INSERT INTO Discount (id, start_date, end_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, discount.getId(), discount.getStartDate(), discount.getEndDate());
        String itemSQL = "INSERT INTO onDiscount (discount_id, category_id) VALUES (?, ?)";
        for (MedicineCategory category : discount.getDiscountedCategories()) {
            jdbcTemplate.update(itemSQL, discount.getId(), category.getId());
        };
    }
}
