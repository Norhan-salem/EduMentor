package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("search") String search) {
        List<User> users = User.findUsersBySearchTerm(search);
        return ResponseEntity.ok(users);
    }
}
