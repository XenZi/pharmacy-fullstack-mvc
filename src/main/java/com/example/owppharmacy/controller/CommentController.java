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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping("/comment")
@Service
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public void postCreate(@ModelAttribute Comment comment, @ModelAttribute String anonymous, String medicineID, HttpSession session, HttpServletResponse response) throws IOException {
        if (anonymous.equals("on")) {
            comment.setAnonymous(true);
        }
        commentService.save(comment,medicineID, session);
        response.sendRedirect("/medicine/view?id="+comment.getMedicine().getId());
    }

    @PostMapping("/update")
    public void postUpdate(@ModelAttribute Comment comment) {
        commentService.update(comment);
    }

    @PostMapping("/delete")
    public void postDelete(@RequestParam int id, @RequestParam String medicineID, HttpServletResponse response) throws IOException {
        commentService.delete(id);
        response.sendRedirect("/medicine/view?id="+medicineID);
    }
}
