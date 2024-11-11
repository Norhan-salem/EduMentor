package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.UserTopicsRequest;
import com.asu.EduMentor.model.Topics;
import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserHasTopics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicsController {

    @PostMapping("/addTopicsToUser")
    public ResponseEntity<Boolean> addTopicsToUser(@RequestBody UserTopicsRequest userTopicsRequest) {
        UserHasTopics userHasTopics = new UserHasTopics();
        User user = userTopicsRequest.getUser();
        Topics topic = userTopicsRequest.getTopics();

        try {
            userHasTopics.addTopic(topic, user);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @DeleteMapping("/deleteTopicsFromUser")
    public ResponseEntity<Boolean> deleteTopicsFromUser(@RequestBody UserTopicsRequest userTopicsRequest) {
        UserHasTopics userHasTopics = new UserHasTopics();
        User user = userTopicsRequest.getUser();
        Topics topic = userTopicsRequest.getTopics();

        try {
            userHasTopics.deleteTopic(topic, user);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/getUserTopics")
    public ResponseEntity<List<Topics>> getUserTopics(@RequestBody User user) {
        UserHasTopics userHasTopics = new UserHasTopics();

        try {
            return  ResponseEntity.ok(userHasTopics.getUserTopics(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

}
