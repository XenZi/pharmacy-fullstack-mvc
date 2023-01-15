package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.IMedicineManufacturerRepository;
import com.example.owppharmacy.models.MedicineManufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MedicineManufacturerRepository implements IMedicineManufacturerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class MedicineManufacturerRowMapper implements RowMapper<MedicineManufacturer> {

        @Override
        public MedicineManufacturer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            String title = resultSet.getString(index++);
            String location = resultSet.getString(index++);
            MedicineManufacturer manufacturer = new MedicineManufacturer();
            manufacturer.setId(id);
            manufacturer.setTitle(title);
            manufacturer.setHqLocation(location);
            return manufacturer;
        }
    }
    @Override
    public List<MedicineManufacturer> findAll() {
        String sql = "SELECT id, title, hqLocation FROM MedicineManufacturer";
        return jdbcTemplate.query(sql, new MedicineManufacturerRowMapper());
    }

    @Override
    public void save(MedicineManufacturer manufacturer) {
        String sql = "INSERT INTO MedicineManufacturer (title, hqLocation) values (?, ?)";
        jdbcTemplate.update(sql, manufacturer.getTitle(), manufacturer.getHqLocation());
    }

    @Override
    public void update(MedicineManufacturer manufacturer) {
        String sql = "UPDATE MedicineManufacturer SET title = ?, hqLocation = ? WHERE id = ?";
        jdbcTemplate.update(sql, manufacturer.getTitle(), manufacturer.getHqLocation(), manufacturer.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM MedicineManufacturer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public MedicineManufacturer findOne(int id) {
        String sql = "SELECT id, title, hqLocation FROM MedicineManufacturer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new MedicineManufacturerRowMapper(), id);
    }
}
