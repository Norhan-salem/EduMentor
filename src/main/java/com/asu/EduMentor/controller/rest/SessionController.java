package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.RegisterMenteeRequest;
import com.asu.EduMentor.controller.rest.body.RegisterMentorRequest;
import com.asu.EduMentor.controller.rest.response.SessionDTO;
import com.asu.EduMentor.logging.LoggingMediator;
import com.asu.EduMentor.logging.MenteeRegisterLog;
import com.asu.EduMentor.logging.MentorRegisterLog;
import com.asu.EduMentor.logging.SessionCreationLog;
import com.asu.EduMentor.model.*;
import com.asu.EduMentor.socialMediaNotifier.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    @PostMapping("/create")
    public ResponseEntity<Boolean> addSession(@RequestBody Session session) {
        try {
            session.create();
            NotificationService.getInstance().notifyObserver();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        LoggingMediator.getInstance().log(new SessionCreationLog(session.getSessionID()));
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @PostMapping("/registerMentor")
    public ResponseEntity<Boolean> registerMentorInSession(@RequestBody RegisterMentorRequest registerMentorRequest) {
        Session session = registerMentorRequest.getSession();
        Mentor mentor = registerMentorRequest.getMentor();
        try {
            session.addMentor(mentor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        LoggingMediator.getInstance().log(new MentorRegisterLog(mentor.getFirstName(), session.getSessionID()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
    }

    @PostMapping("/registerMentee")
    public ResponseEntity<Boolean> registerMenteeInSession(@RequestBody RegisterMenteeRequest registerMenteeRequest) {
        Session session = registerMenteeRequest.getSession();
        Mentee mentee = registerMenteeRequest.getMentee();
        try {
            session.addMentee(mentee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        LoggingMediator.getInstance().log(new MenteeRegisterLog(mentee.getFirstName(), session.getSessionID()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
    }

    @PostMapping("/cancelSession")
    public ResponseEntity<Boolean> cancelSession(@RequestBody Session session) {
        try {
            session.delete((int) session.getSessionID());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
    }

    @PostMapping("/getUserSessions")
    public ResponseEntity<List<Session>> getUserSessions(@RequestBody User user) {
        try {
            if (user.getRole() == UserType.MENTOR) {
                Mentor mentor = new Mentor(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
                return ResponseEntity.ok(mentor.getGivenSessions());
            } else if (user.getRole() == UserType.MENTEE) {
                Mentee mentee = new Mentee(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
                return ResponseEntity.ok(mentee.getAttendedSessions());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @PostMapping("/getSessionMentors")
    public ResponseEntity<List<Mentor>> getSessionMentors(@RequestBody Session session) {
        try {
            return ResponseEntity.ok(session.getSessionMentors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @PostMapping("/getSessionMentees")
    public ResponseEntity<List<Mentee>> getSessionMentees(@RequestBody Session session) {
        try {
            return ResponseEntity.ok(session.getSessionMentees());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @PostMapping("/getSessionFeedback")
    public ResponseEntity<List<Feedback>> getSessionFeedback(@RequestBody Session session) {
        try {
            return ResponseEntity.ok(session.getSessionFeedback());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/getSessions")
    public ResponseEntity<List<SessionDTO>> getSessions() {
        try {
            Session session = new Session();
            List<Session> sessions = (List<Session>) (List<?>) session.readAll();
            return ResponseEntity.ok(SessionDTO.fromSessions(sessions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}
