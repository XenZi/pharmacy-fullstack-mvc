package com.example.owppharmacy.service.impl;

import com.example.owppharmacy.dao.impl.CommentRepository;
import com.example.owppharmacy.models.Comment;
import com.example.owppharmacy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService;

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public void save(Comment comment, String medicineID) {
        User user = userService.findOne(1);
        comment.setUser(user.getAccount());
        comment.setMedicine(medicineService.findOne(medicineID));
        comment.setDateSubmission(LocalDate.now());
        repository.save(comment);
    }

    public void update(Comment comment) {
        repository.update(comment);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public List<Comment> findAllForMedicineID(String id) {
        return repository.findAllForMedicineID(id);
    }
}
