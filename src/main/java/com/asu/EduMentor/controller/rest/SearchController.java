package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.controller.rest.mentorSorter.strategy.MentorSorter;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.NameSortingStrategy;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.TotalHoursSortingStrategy;
import com.asu.EduMentor.controller.rest.response.MentorDTO;
import com.asu.EduMentor.controller.rest.response.SessionDTO;
import com.asu.EduMentor.model.Mentor;
import com.asu.EduMentor.model.Session;
import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("search") String search) {
        List<User> users = User.findUsersBySearchTerm(search);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionDTO>> searchSessions(@RequestParam("search") String search) {
        List<SessionDTO> sessions = SessionDTO.fromSessions(Session.findSessionsBySearchTerm(search));
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<MentorDTO>> searchMentors(@RequestParam("search") String search) {
        List<User> users = User.findUsersBySearchTerm(search);

        List<MentorDTO> mentors = users.stream()
                .filter(user -> user.getRole() == UserType.MENTOR)
                .map(user -> (Mentor) user)
                .peek(mentor -> {
                    double totalHours = mentor.getGivenSessions()
                            .stream()
                            .mapToDouble(Session::getDuration)
                            .sum();
                    mentor.setTotalHours(totalHours);
                })
                .map(MentorDTO::fromMentor)
                .toList();

        return ResponseEntity.ok(mentors);
    }

}
