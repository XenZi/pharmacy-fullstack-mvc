package com.example.owppharmacy.dao;

import com.example.owppharmacy.models.Comment;

import java.util.List;

public interface ICommentRepository {
    public List<Comment> findAll();
    public void save(Comment comment);
    public void update(Comment comment);
    public void delete(int id);
    public List<Comment> findAllForMedicineID(String id);
}
