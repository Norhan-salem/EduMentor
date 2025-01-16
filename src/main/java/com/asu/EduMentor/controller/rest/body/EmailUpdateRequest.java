package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.controller.rest.response.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailUpdateRequest {
    private String email;
    private UserDTO userDTO;
}