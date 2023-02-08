package com.example.owppharmacy.dao.impl;


import com.example.owppharmacy.enums.ELoyaltyCardStatus;
import com.example.owppharmacy.models.Account;
import com.example.owppharmacy.models.LoyaltyCardRequest;
import com.example.owppharmacy.models.MedicineManufacturer;
import com.example.owppharmacy.service.impl.LoyaltyCardService;
import com.example.owppharmacy.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LoyaltyCardRequestRepository {

    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private class LoyaltyCardRequestRowMapper implements RowMapper<LoyaltyCardRequest> {

        @Override
        public LoyaltyCardRequest mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            Account account = userService.findOne(resultSet.getInt(index++));
            ELoyaltyCardStatus status = (ELoyaltyCardStatus) ELoyaltyCardStatus.valueOf(resultSet.getString(index++));
            LoyaltyCardRequest request = new LoyaltyCardRequest();
            request.setAccount(account);
            request.setId(id);
            request.setStatus(status);
            return request;
        }
    }

    public List<LoyaltyCardRequest> findAll() {
        String sql = "SELECT id, user_id, request_status FROM LoyaltyCardRequest";
        return jdbcTemplate.query(sql, new LoyaltyCardRequestRowMapper());
    }
    public void save(int userID) {
        String sql = "INSERT INTO LoyaltyCardRequest (user_id, request_status) VALUES (?, ?);";
        jdbcTemplate.update(sql, userID, ELoyaltyCardStatus.PENDING.toString());
    }

    public LoyaltyCardRequest findOne(int userID) {
        try {
            String sql = "SELECT id, user_id, request_status FROM LoyaltyCardRequest WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, new LoyaltyCardRequestRowMapper(), userID);
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void approveRequest (int id, int userID) {
        String sql = "UPDATE LoyaltyCardRequest SET request_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, ELoyaltyCardStatus.APPROVED.toString(), id);
    }

    public void delete(int id) {
        String sql = "DELETE FROM LoyaltyCardRequest WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public List<LoyaltyCardRequest> findAllByPendingRequest() {
        String sql = "SELECT id, user_id, request_status FROM LoyaltyCardRequest WHERE request_status = 'PENDING'";
        return jdbcTemplate.query(sql, new LoyaltyCardRequestRowMapper());

    }
}
