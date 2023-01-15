package com.example.owppharmacy.controller;

import com.example.owppharmacy.models.Comment;
import com.example.owppharmacy.service.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/comment")
@Service
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public void postCreate(@ModelAttribute Comment comment, String medicineID) {
        commentService.save(comment,medicineID);
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute Comment comment) {
        commentService.update(comment);
    }

    @PostMapping("/delete")
    public void postUpdate(int id) {
        commentService.delete(id);
    }
}
