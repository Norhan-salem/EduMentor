package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.model.Mentee;
import com.asu.EduMentor.model.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentee")
public class MenteeController {

    @GetMapping("/{menteeId}/attendedHours")
    public ResponseEntity<Double> getMenteeAttendedHours(@PathVariable("menteeId") int menteeId) {
        Mentee mentee = (Mentee) new Mentee().read(menteeId);
        if (mentee == null) {
            return ResponseEntity.notFound().build();
        }

        double totalHours = mentee.getAttendedSessions()
                .stream()
                .mapToDouble(Session::getDuration)
                .sum();

        return ResponseEntity.ok(totalHours);
    }
}
