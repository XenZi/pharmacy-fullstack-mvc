package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.ILoyaltyCardRepository;
import com.example.owppharmacy.models.LoyaltyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LoyaltyCardRepository implements ILoyaltyCardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class LoyaltyCardRowMapper implements RowMapper<LoyaltyCard> {

        @Override
        public LoyaltyCard mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            float discount = resultSet.getFloat(index++);
            int points = resultSet.getInt(index++);
            LoyaltyCard loyaltyCard = new LoyaltyCard(id, discount, points);
            return loyaltyCard;
        }
    }
    @Override
    public List<LoyaltyCard> findAll() {
        String sql = "SELECT id, discount, points FROM LoyaltyCard";
        return jdbcTemplate.query(sql, new LoyaltyCardRowMapper());
    }

    @Override
    public void save(LoyaltyCard card) {
        String sql = "INSERT INTO LoyaltyCard (discount, points) VALUES (?, ?)";
        jdbcTemplate.update(sql, card.getDiscount(), card.getPoints());
    }

    @Override
    public void update(LoyaltyCard card) {
        String sql = "UPDATE LoyaltyCard SET discount = ?, points = ? WHERE id = ?";
        jdbcTemplate.update(sql, card.getDiscount(), card.getPoints(), card.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM LoyaltyCard WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public LoyaltyCard findOne(int id) {
        return jdbcTemplate.queryForObject("SELECT id, discount, points FROM LoyaltyCard WHERE id = ?", new LoyaltyCardRowMapper(), id);
    }
}
