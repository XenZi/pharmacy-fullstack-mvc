package com.example.owppharmacy.dao.impl;


import com.example.owppharmacy.enums.EOrderStatus;
import com.example.owppharmacy.models.*;
import com.example.owppharmacy.service.impl.MedicineService;
import com.example.owppharmacy.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class MedicineOrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService;

    private class MedicineOrderRowMapper implements RowMapper<MedicineOrder> {
        @Override
        public MedicineOrder mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            String id = resultSet.getString(index++);
            LocalDateTime creationDate = resultSet.getTimestamp(index++).toLocalDateTime();
            int accountOfPharmacistID = resultSet.getInt(index++);
            EOrderStatus orderStatus = EOrderStatus.valueOf(resultSet.getString(index++));
            String orderFeedback = resultSet.getString(index++);
            Account pharmacistAccount = userService.findOne(accountOfPharmacistID);
            MedicineOrder order = new MedicineOrder();
            order.setId(id);
            order.setCreationDate(creationDate);
            order.setPharmacist(pharmacistAccount);
            order.setStatus(orderStatus);
            order.setOrderFeedback(orderFeedback);
            return order;
        }
    }

    private class MedicineOrderItemRowMapper implements RowMapper<MedicineOrderItem> {
        @Override
        public MedicineOrderItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            Medicine medicine = medicineService.findOne(resultSet.getString(index++));
            String description = resultSet.getString(index++);
            int quantity = resultSet.getInt(index++);
            MedicineOrderItem medicineOrderItem = new MedicineOrderItem();
            medicineOrderItem.setId(id);
            medicineOrderItem.setMedicine(medicine);
            medicineOrderItem.setDescription(description);
            medicineOrderItem.setQuantity(quantity);
            return medicineOrderItem;
        }
    }

    public void saveOrder(MedicineOrder order) {
        String sql = "INSERT INTO MedicineOrder (id, creation_date, pharmacist_id, order_status, order_feedback) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, order.getId(), order.getCreationDate().toString(), order.getPharmacist().getId(), order.getStatus().toString(), order.getOrderFeedback());
    }

    public void saveItem(MedicineOrderItem item, String orderID) {
        String sql = "INSERT INTO MedicineOrderItem (order_id, medicine_id, order_description, quantity) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, orderID, item.getMedicine().getId(), item.getDescription(), item.getQuantity());
    }

    public void updateOrderItems(MedicineOrderItem item) {
        String sql = "UPDATE MedicineOrderItem SET medicine_id = ?, order_description = ?, quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, item.getMedicine().getId(), item.getDescription(), item.getQuantity(), item.getId());
    }

    public List<MedicineOrder> findOrdersByPharmacistID(int pharmacistID) {
        try {
            String sql = "SELECT id, creation_date, pharmacist_id, order_status, order_feedback FROM MedicineOrder WHERE pharmacist_id = ?";
            List<MedicineOrder> orders = jdbcTemplate.query(sql, new MedicineOrderRowMapper(), pharmacistID);
            for (MedicineOrder order : orders) {
                ArrayList orderedItems = (ArrayList) this.findOrderItemsByOrderID(order.getId());
                order.setOrderedItems(orderedItems);
            }
            return orders;
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public List<MedicineOrderItem> findOrderItemsByOrderID(String orderID) {
        try {
            String sql = "SELECT id, medicine_id, order_description, quantity FROM MedicineOrderItem WHERE order_id = ?";
            return jdbcTemplate.query(sql, new MedicineOrderItemRowMapper(), orderID);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public MedicineOrder findOrderByOrderID(String orderID) {
        try {
            String sql = "SELECT id, creation_date, pharmacist_id, order_status, order_feedback FROM MedicineOrder WHERE id = ?";
            MedicineOrder foundOrder = jdbcTemplate.queryForObject(sql, new MedicineOrderRowMapper(), orderID);
            ArrayList orderedItems = (ArrayList) this.findOrderItemsByOrderID(foundOrder.getId());
            foundOrder.setOrderedItems(orderedItems);
            return foundOrder;
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public List<MedicineOrder> findOrdersByStatusPendingReview() {
        try {
            String sql = "SELECT id, creation_date, pharmacist_id, order_status, order_feedback FROM MedicineOrder WHERE order_status = ?";
            List<MedicineOrder> orders = jdbcTemplate.query(sql, new MedicineOrderRowMapper(), EOrderStatus.PENDING_REVIEW.toString());
            for (MedicineOrder order : orders) {
                ArrayList orderedItems = (ArrayList) this.findOrderItemsByOrderID(order.getId());
                order.setOrderedItems(orderedItems);
            }
            return orders;
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void updateOrderFeedback(String feedback, String orderID) {
        String sql = "UPDATE MedicineOrder SET order_feedback = ?, order_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, feedback, EOrderStatus.PENDING_CHANGES.toString(), orderID);
    }

    public void rejectOrder(String feedback, String orderID) {
        String sql = "UPDATE MedicineOrder SET order_feedback = ?, order_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, feedback, EOrderStatus.REJECTED.toString(), orderID);
    }

    public void approveOrder(String orderID) {
        String sql = "UPDATE MedicineOrder SET order_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, EOrderStatus.APPROVED.toString(), orderID);
    }

    public void updateOrderStatusToPendingReview(String orderID) {
        String sql = "UPDATE MedicineOrder SET order_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, EOrderStatus.PENDING_REVIEW.toString(), orderID);
    }
}
