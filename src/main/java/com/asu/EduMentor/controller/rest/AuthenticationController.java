package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.CredentialsBody;
import com.asu.EduMentor.controller.rest.body.SignUpBody;
import com.asu.EduMentor.controller.rest.response.AuthResponse;
import com.asu.EduMentor.logging.LoggingMediator;
import com.asu.EduMentor.logging.SignupLog;
import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserFactory;
import com.asu.EduMentor.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody CredentialsBody credentials) {
        User user = User.findByEmail(credentials.getEmail());

        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            return ResponseEntity.ok(AuthResponse.success(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AuthResponse.error("Invalid email or password"));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpBody userBody) {
        try {
            UserType userType = UserType.fromRoleId(userBody.getUserType());
            log.info("Creating new user of type: " + userType);

            User newUser = UserFactory.createUser(
                    userType,
                    userBody.getFirstName(),
                    userBody.getLastName(),
                    userBody.getCredentials().getEmail(),
                    userBody.getCredentials().getPassword()
            );
            newUser.create();
            LoggingMediator.getInstance().log(new SignupLog(newUser.getFirstName()));
            return ResponseEntity.ok(AuthResponse.success(newUser));
        } catch (Exception e) {
            log.error("Error during signup", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AuthResponse.error("Error creating user: " + e.getMessage()));
        }
    }
}