package com.asu.EduMentor.controller.rest.response;

import com.asu.EduMentor.model.Mentor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MentorDTO extends UserDTO {
    private double totalHours;

    public static MentorDTO fromMentor(Mentor mentor) {
        MentorDTO dto = new MentorDTO();
        dto.setUserID(mentor.getUserID());
        dto.setFirstName(mentor.getFirstName());
        dto.setLastName(mentor.getLastName());
        dto.setEmail(mentor.getEmail());
        dto.setUserType(mentor.getRole());
        dto.setTotalHours(mentor.getTotalHours());
        return dto;
    }

    public static List<MentorDTO> fromMentors(List<Mentor> mentors) {
        return mentors.stream().map(MentorDTO::fromMentor).toList();
    }
}