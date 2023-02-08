package com.example.owppharmacy.dao.impl;

import com.example.owppharmacy.dao.ICommentRepository;
import com.example.owppharmacy.models.*;
import com.example.owppharmacy.service.impl.MedicineService;
import com.example.owppharmacy.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CommentRepository implements ICommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private UserService userService;

    private class CommentRowMapper implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int index = 1;
            int id = resultSet.getInt(index++);
            String description = resultSet.getString(index++);
            LocalDate dateSubmission = resultSet.getDate(index++).toLocalDate();
            Account account = userService.findOne(resultSet.getInt(index++));
            Medicine medicine = medicineService.findOne(resultSet.getString(index++));
            boolean isAnonymous = resultSet.getBoolean(index++);
            float rating = resultSet.getFloat(index++);
            Comment comment = new Comment();
            comment.setId(id);
            comment.setDescription(description);
            comment.setDateSubmission(dateSubmission);
            comment.setUser(account);
            comment.setMedicine(medicine);
            comment.setAnonymous(isAnonymous);
            comment.setRating(rating);

            return comment;
        }
    }


    @Override
    public List<Comment> findAll() {
        String sql = "SELECT id, description, dateSubmission, user_id, medicine_id, anonymous, rating FROM Comment";
        return jdbcTemplate.query(sql, new CommentRowMapper());
    }


    @Override
    public void save(Comment comment) {
        String sql = "INSERT INTO Comment (description, dateSubmission, user_id, medicine_id, anonymous, rating) values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, comment.getDescription(), comment.getDateSubmission(), comment.getUser().getId(), comment.getMedicine().getId(), comment.isAnonymous(), comment.getRating());
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE Comment SET description = ?, isAnonymous = ?, rating = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getDescription(), comment.isAnonymous(), comment.getRating(), comment.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Comment WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Comment> findAllForMedicineID(String id) {
        String sql = "SELECT id, description, dateSubmission, user_id, medicine_id, anonymous, rating FROM Comment WHERE medicine_id = ?";

        return jdbcTemplate.query(sql, new CommentRowMapper(), id);
    }


}
