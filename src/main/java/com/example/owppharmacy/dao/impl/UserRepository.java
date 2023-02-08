package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.IUserRepository;
import com.example.owppharmacy.enums.ERole;
import com.example.owppharmacy.models.*;
import com.example.owppharmacy.service.impl.LoyaltyCardService;
import com.example.owppharmacy.service.impl.ShoppingHistoryService;
import com.example.owppharmacy.service.impl.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    private class UserRowMapper implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            String username = resultSet.getString(index++);
            String password = resultSet.getString(index++);
            String email = resultSet.getString(index++);
            String firstName = resultSet.getString(index++);
            String lastName = resultSet.getString(index++);
            LocalDate dateOfBirth = resultSet.getDate(index++).toLocalDate();
            String address = resultSet.getString(index++);
            String phoneNumber = resultSet.getString(index++);
            LocalDateTime registrationDate = resultSet.getTimestamp(index++).toLocalDateTime();
            ERole role = ERole.valueOf(resultSet.getString(index++));
            boolean blocked = resultSet.getBoolean(index++);
            Account account = new Account(id, username, password, email, firstName, lastName, dateOfBirth, address, phoneNumber, registrationDate, role, blocked);
            return account;
        }
    }

    public Account findOne(int id) {
        try {
            String sql = "SELECT id, username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked FROM User WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<Account> findAll() {
        String sql =
                "SELECT id, username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked FROM User";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public Account findOne(String username, String password) {
        try {
            String sql =
                    "SELECT id, username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked FROM User WHERE username = ? and password = ?";
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username, password);
        }
        catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void save(Account account) {

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO User (username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, account.getUsername());
                preparedStatement.setString(index++, account.getPassword());
                preparedStatement.setString(index++, account.getEmail());
                preparedStatement.setString(index++, account.getFirstName());
                preparedStatement.setString(index++, account.getLastName());
                preparedStatement.setString(index++, account.getDateOfBirth().toString());
                preparedStatement.setString(index++, account.getAddress());
                preparedStatement.setString(index++, account.getPhoneNumber());
                preparedStatement.setString(index++, Timestamp.valueOf(LocalDateTime.now()).toString());
                preparedStatement.setString(index++, ERole.USER.toString());
                preparedStatement.setBoolean(index++, account.isBlocked());
                return preparedStatement;
            }
        };
        jdbcTemplate.update(preparedStatementCreator);
        Account account1 = this.findOne(account.getUsername(), account.getPassword());
        wishListService.saveWishList(account1.getId());
        shoppingHistoryService.save(account1.getId());
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE User SET password = ?, firstName = ?, lastName = ?, dateOfBirth = ?, address = ?, phNumber = ? WHERE id = ?";
        jdbcTemplate.update(sql, account.getPassword(), account.getFirstName(), account.getLastName(), account.getDateOfBirth(), account.getAddress(), account.getPhoneNumber(), account.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void block(int id) {
        String sql = "UPDATE User SET blocked=1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void unblock(int id) {
        String sql = "UPDATE USER SET blocked=0 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Account> findAllBySearchCriteria(String username, String role, String orderBy) {
        StringBuilder query = new StringBuilder("SELECT id, username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked FROM User ");
        StringBuilder addedQuery = new StringBuilder();
        StringBuilder orderByClause = new StringBuilder();
        boolean doesPreviousExist = false;
        if (!username.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append(" AND username LIKE '%").append(username).append("%'");
            }
            else {
                addedQuery.append(" username LIKE '%").append(username).append("%'");
            }
        }
        if (!role.equals("-1")) {
            if (doesPreviousExist) {
                addedQuery.append("AND userRole LIKE '%").append(role).append("%'");
            }
            else {
                addedQuery.append(" userRole LIKE '%").append(role).append("%'");
            }
        }
        if (!orderBy.equals("-1")) {
            switch (orderBy) {
                case "username":
                    orderByClause.append("ORDER BY username");
                    break;
                case "role":
                    orderByClause.append("ORDER BY userRole");
                    break;
            }
        }

        if (!addedQuery.isEmpty()) {
            query.append("WHERE " + addedQuery);
        }

        if (!orderByClause.isEmpty()) {
            query.append(" " + orderByClause);
        }

        return  jdbcTemplate.query(query.toString(), new UserRowMapper());
    }
}

