package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.CredentialsBody;
import com.asu.EduMentor.controller.rest.body.SignUpBody;
import com.asu.EduMentor.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody CredentialsBody credentials) {
        User user = User.findByEmail(credentials.getEmail());

        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody SignUpBody userBody) {
        UserType userType = UserType.fromRoleId(userBody.getRole());
        User newUser = switch (userType) {
            case ADMIN -> new Admin(userBody.getFirstName(), userBody.getLastName(),
                    userBody.getCredentials().getEmail(), userBody.getCredentials().getPassword());
            case MENTOR -> new Mentor(userBody.getFirstName(), userBody.getLastName(),
                    userBody.getCredentials().getEmail(), userBody.getCredentials().getPassword());
            case MENTEE -> new Mentee(userBody.getFirstName(), userBody.getLastName(),
                    userBody.getCredentials().getEmail(), userBody.getCredentials().getPassword());
            case ONLINEDONOR -> new OnlineDonor(userBody.getFirstName(), userBody.getLastName(),
                    userBody.getCredentials().getEmail(), userBody.getCredentials().getPassword());
        };

        newUser.create();
        return ResponseEntity.ok(true);
    }
}