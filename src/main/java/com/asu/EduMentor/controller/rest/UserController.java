package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.controller.rest.body.EmailUpdateRequest;
import com.asu.EduMentor.controller.rest.body.ErrorResponse;
import com.asu.EduMentor.controller.rest.body.NameUpdateRequest;
import com.asu.EduMentor.controller.rest.response.UserDTO;
import com.asu.EduMentor.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody EmailUpdateRequest request) {
        try {
            UserDTO userDTO = request.getUserDTO();
            User existingUser = User.findByEmail(userDTO.getEmail());
            if (existingUser == null) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("User not found"));
            }

            User emailCheck = User.findByEmail(request.getEmail());
            if (emailCheck != null && emailCheck.getUserID() != existingUser.getUserID()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Email already in use"));
            }

            existingUser.setEmail(request.getEmail());

            User updatedUser = (User) existingUser.update(existingUser);
            UserDTO updatedUserDTO = UserDTO.fromUser(updatedUser);

            return ResponseEntity.ok(updatedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Error updating email: " + e.getMessage()));
        }
    }

    @PostMapping("/updateName")
    public ResponseEntity<?> updateName(@RequestBody NameUpdateRequest request) {
        try {
            UserDTO userDTO = request.getUserDTO();
            User existingUser = User.findByEmail(userDTO.getEmail());
            if (existingUser == null) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("User not found"));
            }

            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());

            User updatedUser = (User) existingUser.update(existingUser);
            UserDTO updatedUserDTO = UserDTO.fromUser(updatedUser);

            return ResponseEntity.ok(updatedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Error updating name: " + e.getMessage()));
        }
    }
}