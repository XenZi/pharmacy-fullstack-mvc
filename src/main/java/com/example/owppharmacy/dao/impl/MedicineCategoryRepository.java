package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.IMedicineCategoryRepository;
import com.example.owppharmacy.models.MedicineCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MedicineCategoryRepository implements IMedicineCategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class MedicineCategoryRowMapper implements RowMapper<MedicineCategory> {

        @Override
        public MedicineCategory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            String title = resultSet.getString(index++);
            String purpose = resultSet.getString(index++);
            String description = resultSet.getString(index++);
            MedicineCategory category = new MedicineCategory();
            category.setId(id);
            category.setTitle(title);
            category.setPurpose(purpose);
            category.setDescription(description);
            return category;
        }
    }

    @Override
    public List<MedicineCategory> findAll() {
        String sql = "SELECT id, title, purpose, description FROM MedicineCategory";
        return jdbcTemplate.query(sql, new MedicineCategoryRowMapper());
    }

    @Override
    public void save(MedicineCategory category) {
        String sql = "INSERT INTO MedicineCategory (title, purpose, description) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, category.getTitle(), category.getPurpose(), category.getDescription());
    }

    @Override
    public void update(MedicineCategory category) {
        String sql = "UPDATE MedicineCategory SET title = ?, purpose = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, category.getTitle(), category.getPurpose(), category.getDescription(), category.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM MedicineCategory WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public MedicineCategory findOne(int id) {
        String sql = "SELECT id, title, purpose, description FROM MedicineCategory WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new MedicineCategoryRowMapper(), id);
    }
}
