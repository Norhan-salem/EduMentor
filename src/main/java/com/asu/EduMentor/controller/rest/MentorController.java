package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.MentorAvailabilityRequest;
import com.asu.EduMentor.controller.rest.response.MentorDTO;
import com.asu.EduMentor.controller.rest.response.SessionDTO;
import com.asu.EduMentor.model.Availability;
import com.asu.EduMentor.model.Mentor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mentor")
public class MentorController {
    @PostMapping("/getMentorAvailability")
    public ResponseEntity<List<Availability>> getMentorAvailability(@RequestBody Mentor mentor) {
        try {
            return ResponseEntity.ok(mentor.getMentorAvailabilities());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @PostMapping("/addMentorAvailability")
    public ResponseEntity<Boolean> addMentorAvailability(
            @RequestBody MentorAvailabilityRequest mentorAvailabilityRequest
    ) {
        Mentor mentor = mentorAvailabilityRequest.getMentor();
        Availability availability = mentorAvailabilityRequest.getAvailability();
        try {
            mentor.addAvailability(availability);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/deleteMentorAvailability")
    public ResponseEntity<Boolean> deleteMentorAvailability(
            @RequestBody MentorAvailabilityRequest mentorAvailabilityRequest
    ) {
        Mentor mentor = mentorAvailabilityRequest.getMentor();
        Availability availability = mentorAvailabilityRequest.getAvailability();
        try {
            mentor.deleteAvailability(availability);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/getMentoringHours")
    public ResponseEntity<Double> getMentoringHours(@RequestBody Mentor mentor) {
        try {
            double totalHours = mentor.getTotalHours();
            return ResponseEntity.ok().body(totalHours);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0d);
        }
    }

    @PostMapping("/getAvailableMentors")
    public ResponseEntity<List<MentorDTO>> getAvailableMentors(@RequestBody SessionDTO sessionDTO) {
        try {
            List<Mentor> availableMentors = Mentor.findAvailableMentors(sessionDTO.getDate(), sessionDTO.getDuration());
            return ResponseEntity.ok(MentorDTO.fromMentors(availableMentors));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}
