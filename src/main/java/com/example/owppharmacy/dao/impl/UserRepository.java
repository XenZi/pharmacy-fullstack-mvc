package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.IUserRepository;
import com.example.owppharmacy.enums.ERole;
import com.example.owppharmacy.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
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

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
                    User user = new User();
                    user.setAccount(account);

                    return user;
            }
        }

    public User findOne(int id) {
        try {
            String sql = "SELECT id, username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked FROM User WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        String sql =
                "SELECT id, username, password, email, firstName, lastName, dateOfBirth, address, phNumber, registrationDate, userRole, blocked FROM User";
        return jdbcTemplate.query(sql, new UserRowMapper());
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
}
