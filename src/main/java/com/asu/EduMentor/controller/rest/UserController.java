package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.controller.rest.body.EmailUpdateRequest;
import com.asu.EduMentor.controller.rest.body.ErrorResponse;
import com.asu.EduMentor.controller.rest.body.NameUpdateRequest;
import com.asu.EduMentor.controller.rest.response.UserDTO;
import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody EmailUpdateRequest request) {
        try {
            UserDTO userDTO = request.getUserDTO();
            User user = UserFactory.createUser(userDTO.getUserType(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), null);
            user.setUserID(userDTO.getUserID());

            User existingUser = User.findByEmail(request.getEmail());
            if (existingUser != null && existingUser.getUserID() != user.getUserID()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Email already in use"));
            }
            user.setEmail(request.getEmail());
            User updatedUser = (User) user.update(user);
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
            User user = UserFactory.createUser(userDTO.getUserType(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), null);
            user.setUserID(userDTO.getUserID());

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            User updatedUser = (User) user.update(user);

            UserDTO updatedUserDTO = UserDTO.fromUser(updatedUser);

            return ResponseEntity.ok(updatedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse("Error updating name: " + e.getMessage()));
        }
    }
}






