package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.UserTopicsRequest;
import com.asu.EduMentor.controller.rest.response.UserDTO;
import com.asu.EduMentor.model.Topics;
import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserFactory;
import com.asu.EduMentor.model.UserTopics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicsController {

    @PostMapping("/addTopicsToUser")
    public ResponseEntity<Boolean> addTopicsToUser(@RequestBody UserTopicsRequest userTopicsRequest) {
        UserTopics userTopics = new UserTopics();
        User user = UserFactory.createUser(userTopicsRequest.getUserDTO().getUserType(), userTopicsRequest.getUserDTO().getFirstName(), userTopicsRequest.getUserDTO().getLastName(), userTopicsRequest.getUserDTO().getEmail(),null);
        user.setUserID(userTopicsRequest.getUserDTO().getUserID());
        Topics topic = userTopicsRequest.getTopic();

        try {
            userTopics.addTopic(topic, user);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @DeleteMapping("/deleteTopicsFromUser")
    public ResponseEntity<Boolean> deleteTopicsFromUser(@RequestBody UserTopicsRequest userTopicsRequest) {
        UserTopics userTopics = new UserTopics();
        User user = UserFactory.createUser(userTopicsRequest.getUserDTO().getUserType(), userTopicsRequest.getUserDTO().getFirstName(), userTopicsRequest.getUserDTO().getLastName(), userTopicsRequest.getUserDTO().getEmail(),null);
        Topics topic = userTopicsRequest.getTopic();

        try {
            userTopics.deleteTopic(topic, user);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/getUserTopics")
    public ResponseEntity<List<Topics>> getUserTopics(@RequestBody UserDTO userDTO) {
        UserTopics userTopics = new UserTopics();
        User user = UserFactory.createUser(userDTO.getUserType(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), null);
        user.setUserID(userDTO.getUserID());
        try {
            return  ResponseEntity.ok(userTopics.getUserTopics(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
    @GetMapping("/getAllTopics")
    public ResponseEntity<List<Topics>> getAllTopics() {
        Topics topic = new Topics();
        List<Object> topics = topic.readAll();
        try {
            return ResponseEntity.ok((List<Topics>) (Object) topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

}
