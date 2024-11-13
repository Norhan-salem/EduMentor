package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.model.Session;
import com.asu.EduMentor.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    MentorSorter mentorSorter;

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("search") String search) {
        List<User> users = User.findUsersBySearchTerm(search);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> searchSessions(@RequestParam("search") String search) {
        List<Session> sessions = Session.findSessionsBySearchTerm(search);
        return ResponseEntity.ok(sessions);
    }
}
