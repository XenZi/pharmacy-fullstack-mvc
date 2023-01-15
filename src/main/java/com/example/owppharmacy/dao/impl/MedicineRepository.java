package com.example.owppharmacy.dao.impl;


import com.example.owppharmacy.dao.IMedicineRepository;
import com.example.owppharmacy.enums.EMedicineForm;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.MedicineCategory;
import com.example.owppharmacy.models.MedicineManufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MedicineRepository implements IMedicineRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MedicineManufacturerRepository manufacturerDAO;
    @Autowired
    private MedicineCategoryRepository categoryDAO;

    private class MedicineRowMapper implements RowMapper<Medicine> {

        @Override
        public Medicine mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            String id = resultSet.getString(index++);
            String title = resultSet.getString(index++);
            String description = resultSet.getString(index++);
            String contraindications = resultSet.getString(index++);
            EMedicineForm form = EMedicineForm.valueOf(resultSet.getString(index++));
            float avgRating = resultSet.getFloat(index++);
            String image = resultSet.getString(index++);
            int quantity = resultSet.getInt(index++);
            float price = resultSet.getFloat(index++);
            int manufacturerID = resultSet.getInt(index++);
            int categoryID = resultSet.getInt(index++);
            boolean isApproved = resultSet.getBoolean(index++);

            Medicine medicine = new Medicine();
            medicine.setId(id);
            medicine.setTitle(title);
            medicine.setDescription(description);
            medicine.setContraindications(contraindications);
            medicine.setMedicineForm(form);
            medicine.setAvgRating(avgRating);
            medicine.setPicture("http://localhost:8080/uploads/images?imageName=" + image);
            medicine.setQuantity(quantity);
            medicine.setPrice(price);
            medicine.setApproved(isApproved);

            if (manufacturerID != 0) {
                MedicineManufacturer manufacturer = manufacturerDAO.findOne(manufacturerID);
                medicine.setManufacturer(manufacturer);
            }

            if (categoryID != 0) {
                MedicineCategory category = categoryDAO.findOne(categoryID);
                medicine.setCategory(category);
            }
            return medicine;
        }
    }

    @Override
    public List<Medicine> findAll() {
        String sql = "SELECT id, title, description, contraindications, medicineForm, avgRating, picture, quantity, price, manufacturer_id, category_id, approved FROM MEDICINE";
        return jdbcTemplate.query(sql, new MedicineRowMapper());
    }

    @Override
    public void save(Medicine medicine) {
        String sql = "INSERT INTO Medicine (id, title, description, contraindications, medicineForm, avgRating, picture, quantity, price, manufacturer_id, category_id, approved) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,medicine.getId(), medicine.getTitle(), medicine.getDescription(), medicine.getContraindications(), medicine.getMedicineForm().toString(), medicine.getAvgRating(), medicine.getPicture(), medicine.getQuantity(), medicine.getPrice(), medicine.getManufacturer().getId(), medicine.getCategory().getId(), medicine.isApproved());
    }

    @Override
    public void update(Medicine medicine) {
        String sql = "UPDATE Medicine SET title = ?, description = ?, contraindications = ?, medicineForm = ?, avgRating = ?, picture = ?, quantity = ?, price = ?, manufacturer_id = ?, category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, medicine.getTitle(), medicine.getDescription(), medicine.getContraindications(), medicine.getMedicineForm().toString(), medicine.getAvgRating(), medicine.getPicture(), medicine.getQuantity(), medicine.getPrice(), medicine.getManufacturer().getId(), medicine.getCategory().getId(), medicine.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Medicine WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Medicine findOne(String id) {
        String sql = "SELECT id, title, description, contraindications, medicineForm, avgRating, picture, quantity, price, manufacturer_id, category_id, approved FROM MEDICINE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new MedicineRowMapper(), id);
    }
}
