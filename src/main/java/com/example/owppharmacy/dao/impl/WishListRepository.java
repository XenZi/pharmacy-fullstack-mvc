package com.example.owppharmacy.dao.impl;


import com.example.owppharmacy.dao.IWishListRepository;
import com.example.owppharmacy.enums.EMedicineForm;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.MedicineCategory;
import com.example.owppharmacy.models.MedicineManufacturer;
import com.example.owppharmacy.models.WishList;
import com.example.owppharmacy.service.impl.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WishListRepository implements IWishListRepository {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class WishListRowMapper implements RowMapper<WishList> {
        @Override
        public WishList mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            WishList wishList = new WishList();
            wishList.setId(id);
            return wishList;
        }
    }

    private class WishListItemRowCallBackHandler implements RowCallbackHandler {

        private Map<String, Medicine> medicines = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            String medicineID = resultSet.getString(index++);
            Medicine medicine = medicines.get(medicineID);
            if (medicine == null) {
                medicine = medicineService.findOne(medicineID);
                medicines.put(medicineID, medicine);
            }
        }

        public ArrayList<Medicine> getMedicines() {
            return new ArrayList<>(medicines.values());
        }

    }

    @Override
    public WishList findOne(int id) {
        return null;
    }

    @Override
    public WishList findOneByUserID(int userID) {
        try {
            String sql = "SELECT id FROM WishList WHERE user_id = ?";
            WishList wishList = jdbcTemplate.queryForObject(sql, new WishListRowMapper(), userID);
            if (wishList != null) {
                String wishItemSQL = "SELECT medicine_id FROM WishItem WHERE list_id = ?";
                WishListItemRowCallBackHandler wishListItemRowCallBackHandler = new WishListItemRowCallBackHandler();
                jdbcTemplate.query(wishItemSQL, wishListItemRowCallBackHandler, wishList.getId());
                wishList.setMedicine(wishListItemRowCallBackHandler.getMedicines());
            }
            return wishList;
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }

    }

    @Override
    public void deleteItem(int listID, String medicineID) {
        String sql  = "DELETE FROM WishItem WHERE list_id = ? and medicine_id = ?;";
        jdbcTemplate.update(sql, listID, medicineID);
    }

    @Override
    public void saveItem(int listID, String medicineID) {
        String sql = "INSERT INTO WishItem (list_id, medicine_id) VALUES (?, ?);";
        jdbcTemplate.update(sql, listID, medicineID);
    }

    @Override
    public void saveWishList(int userID) {
        String sql = "INSERT INTO WishList (user_id) VALUES (?)";
        jdbcTemplate.update(sql, userID);
    }

}
