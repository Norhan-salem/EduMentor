package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.controller.rest.response.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameUpdateRequest {
    private String firstName;
    private String lastName;
    private UserDTO userDTO;
}