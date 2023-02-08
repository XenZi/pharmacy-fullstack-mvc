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
            medicine.setPicture(image);
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
        String sql = "SELECT m.id, title, m.description, contraindications, medicineForm, IFNULL(avg(c.rating), 0) as avg_rating, picture, quantity, price, manufacturer_id, category_id, approved FROM Medicine as m LEFT OUTER JOIN Comment as c ON c.medicine_id = m.id GROUP BY m.id;";
        return jdbcTemplate.query(sql, new MedicineRowMapper());
    }

    @Override
    public void save(Medicine medicine) {
        String sql = "INSERT INTO Medicine (id, title, description, contraindications, medicineForm, picture, quantity, price, manufacturer_id, category_id, approved) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,medicine.getId(), medicine.getTitle(), medicine.getDescription(), medicine.getContraindications(), medicine.getMedicineForm().toString(), medicine.getPicture(), medicine.getQuantity(), medicine.getPrice(), medicine.getManufacturer().getId(), medicine.getCategory().getId(), medicine.isApproved());
    }

    @Override
    public void update(Medicine medicine) {
        String sql = "UPDATE Medicine SET title = ?, description = ?, contraindications = ?, medicineForm = ?,price = ?, manufacturer_id = ?, category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, medicine.getTitle(), medicine.getDescription(), medicine.getContraindications(), medicine.getMedicineForm().toString(), medicine.getPrice(), medicine.getManufacturer().getId(), medicine.getCategory().getId(), medicine.getId());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Medicine WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Medicine findOne(String id) {
        String sql = "SELECT m.id, title, m.description, contraindications, medicineForm, IFNULL(avg(c.rating), 0) as avg_rating, picture, quantity, price, manufacturer_id, category_id, approved FROM Medicine as m LEFT OUTER JOIN Comment as c ON c.medicine_id = m.id WHERE m.id = ? GROUP BY m.id;";
        return jdbcTemplate.queryForObject(sql, new MedicineRowMapper(), id);
    }

    @Override
    public void approveMedicine(String id) {
        String sql = "UPDATE Medicine SET approved = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Medicine> findAllByUnapprovedStatus() {
        String sql = "SELECT m.id, title, m.description, contraindications, medicineForm, IFNULL(avg(c.rating), 0) as avg_rating, picture, quantity, price, manufacturer_id, category_id, approved FROM Medicine as m LEFT OUTER JOIN Comment as c ON c.medicine_id = m.id WHERE approved = 0 GROUP BY m.id;";
        return jdbcTemplate.query(sql, new MedicineRowMapper());
    }

    public void updateQuantity(String medicineID, int quantity) {
        String sql = "UPDATE Medicine SET quantity = ? WHERE id = ?";
        int newQt = this.findOne(medicineID).getQuantity() + quantity;
        jdbcTemplate.update(sql, newQt, medicineID);
    }

    public List<Medicine> findAllBySearchCriteria(String medicineName, String categoryID, String priceFrom,  String priceTo, String manufacturerID,  String contraindications, String description, String  medicineForm, String avgRating, String medicineID, String sortBy) {
        StringBuilder query = new StringBuilder("SELECT m.id, title, m.description, contraindications, medicineForm, IFNULL(avg(c.rating), 0) as avg_rating, picture, quantity, price, manufacturer_id, category_id, approved FROM Medicine as m LEFT OUTER JOIN Comment as c ON c.medicine_id = m.id ");
        StringBuilder addedQuery = new StringBuilder();
        StringBuilder havingClause = new StringBuilder();
        StringBuilder orderByClause = new StringBuilder();
        boolean doesPreviousExist = false;
        if (!medicineName.equals("-1")) {
            addedQuery.append("title LIKE '%").append(medicineName).append("%'");
            doesPreviousExist = true;
        }
        if (!categoryID.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append(" AND category_id = ").append(Integer.parseInt(categoryID));
            } else {
                addedQuery.append(" category_id = ").append(Integer.parseInt(categoryID));
            }
            doesPreviousExist = true;
        }
        if (!priceFrom.equals("-1") && !priceTo.equals("-1")) {
            if (!doesPreviousExist) {
                addedQuery.append(" price ").append("BETWEEN ").append(Float.parseFloat(priceFrom)).append(" AND ").append(Float.parseFloat(priceTo));
            }
            else {
                addedQuery.append(" AND price ").append("BETWEEN ").append(Float.parseFloat(priceFrom)).append(" AND ").append(Float.parseFloat(priceTo));
            }
            doesPreviousExist = true;
        }
        if (!manufacturerID.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append(" AND manufacturer_id = ").append(manufacturerID);
            }
            else {
                addedQuery.append(" manufacturer_id = ").append(manufacturerID);
            }
            doesPreviousExist = true;
        }
        if (!priceFrom.equals("-1") && priceTo.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append(" AND price >= ").append(Float.parseFloat(priceFrom));
            } else {
                addedQuery.append(" price >= ").append(Float.parseFloat(priceFrom));
            }
            doesPreviousExist = true;
        }
        if (!priceTo.equals("-1") && priceFrom.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append(" AND price <=").append(Float.parseFloat(priceTo));
            } else {
                addedQuery.append(" price <=").append(Float.parseFloat(priceTo));
            }
            doesPreviousExist = true;
        }
        if(!contraindications.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append("AND contraindications LIKE '%").append(contraindications).append("%'");
            } else {
                addedQuery.append("contraindications LIKE '%").append(contraindications).append("%'");
            }
            doesPreviousExist = true;
        }
        if(!description.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append("AND m.description LIKE '%").append(description).append("%'");
            } else {
                addedQuery.append("m.description LIKE '%").append(description).append("%'");
            }
            doesPreviousExist = true;
        }
        if(!medicineForm.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append("AND medicineForm LIKE '%").append(medicineForm).append("%'");
            } else {
                addedQuery.append("medicineForm LIKE '%").append(medicineForm).append("%'");
            }
            doesPreviousExist = true;
        }
        if(!avgRating.equals("-1")) {
            if (doesPreviousExist) {
                havingClause.append("HAVING avg_rating >= ").append(Float.parseFloat(avgRating));
            } else {
                havingClause.append("HAVING avg_rating >= ").append(Float.parseFloat(avgRating));
            }
            doesPreviousExist = true;
        }
        if(!medicineID.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append("AND m.id = '").append(medicineID).append("'");
            } else {
                addedQuery.append("m.id = '").append(medicineID).append("'");
            }
            doesPreviousExist = true;
        }
        if (!sortBy.equals("-1")) {
            switch (sortBy) {
                case "medicineName":
                    orderByClause.append(" ORDER BY ").append("title");
                    break;
                case "category":
                    orderByClause.append(" ORDER BY ").append("category_id");
                    break;
                case "priceIncrease":
                    orderByClause.append(" ORDER BY ").append("price ASC");
                    break;
                case "priceDecrease":
                    orderByClause.append(" ORDER BY ").append("price DESC");
                    break;
                case "manufacturer":
                    orderByClause.append(" ORDER BY ").append("manufacturer_id");
                    break;
                case "contraindications":
                    orderByClause.append(" ORDER BY ").append("contraindications");
                    break;
                case "description":
                    orderByClause.append(" ORDER BY ").append("description");
                    break;
                case "form":
                    orderByClause.append(" ORDER BY ").append("medicineForm");
                    break;
                case "avgRatingAsc":
                    orderByClause.append(" ORDER BY ").append("avg_rating ASC");
                    break;
                case "avgRatingDsc":
                    orderByClause.append(" ORDER BY ").append("avg_rating DESC");
                    break;
            }
        }
        if (!addedQuery.isEmpty()) {
            query.append("WHERE " + addedQuery);
        }
        query.append(" GROUP BY m.id");
        if (!havingClause.isEmpty()) {
            query.append(" " + havingClause);
        }
        if (!orderByClause.isEmpty()) {
            query.append(" " + orderByClause);
        }
        return jdbcTemplate.query(query.toString(), new MedicineRowMapper());
    }
}
