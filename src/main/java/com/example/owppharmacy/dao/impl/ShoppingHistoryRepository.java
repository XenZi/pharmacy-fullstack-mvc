package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.IShoppingHistoryRepository;
import com.example.owppharmacy.models.Medicine;
import com.example.owppharmacy.models.ShoppingHistory;
import com.example.owppharmacy.models.ShoppingHistoryItem;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShoppingHistoryRepository implements IShoppingHistoryRepository {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ShoppingHistoryRowMapper implements RowMapper<ShoppingHistory> {
        @Override
        public ShoppingHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            ShoppingHistory shoppingHistory = new ShoppingHistory();
            shoppingHistory.setId(id);
            return shoppingHistory;
        }
    }
    private class ShoppingHistoryItemRowCallBackHandler implements RowCallbackHandler {

        private LinkedHashMap<Integer, ShoppingHistoryItem> histories = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            int id = rs.getInt(index++);
            Medicine medicine = medicineService.findOne(rs.getString(index++));
            LocalDate shoppingDate = rs.getDate(index++).toLocalDate();
            ShoppingHistoryItem shoppingHistoryItem = histories.get(id);
            if (shoppingHistoryItem == null) {
                shoppingHistoryItem = new ShoppingHistoryItem(id, medicine, shoppingDate);
                histories.put(id, shoppingHistoryItem);
            }
        }

        public ArrayList<ShoppingHistoryItem> getItems() {
            return new ArrayList<>(histories.values());
        }
    }
    @Override
    public ShoppingHistory findOneByUserID(int id) {
        try {
            String sql = "SELECT id FROM ShoppingHistory WHERE user_id = ?";
            ShoppingHistory shoppingHistory = jdbcTemplate.queryForObject(sql, new ShoppingHistoryRowMapper(), id);
            if (shoppingHistory != null) {
                String shoppingHistoryItemSQL = "SELECT id, medicine_id, shopping_date FROM ShoppingHistoryItem WHERE shopping_id = ?";
                ShoppingHistoryItemRowCallBackHandler shoppingHistoryItemRowCallBackHandler = new ShoppingHistoryItemRowCallBackHandler();
                jdbcTemplate.query(shoppingHistoryItemSQL, shoppingHistoryItemRowCallBackHandler, shoppingHistory.getId());
                shoppingHistory.setHistory(shoppingHistoryItemRowCallBackHandler.getItems());
            }
            return shoppingHistory;
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void save(int id) {
        String sql = "INSERT INTO ShoppingHistory (user_id) VALUES (?);";
        jdbcTemplate.update(sql, id);
    }

    public void saveItem(String medicineID, int userShoppingHistoryID, LocalDate localDate, int quantity) {
        String sql = "INSERT INTO ShoppingHistoryItem (shopping_id, medicine_id, shopping_date, quantity) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, userShoppingHistoryID, medicineID, localDate, quantity);
    }
}
