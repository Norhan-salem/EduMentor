package com.asu.EduMentor.controller.rest.response;

import com.asu.EduMentor.model.Session;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SessionDTO {
    private long sessionID;
    private Date date;
    private double duration;
    private String name;

    public static SessionDTO fromSession(Session session) {
        SessionDTO dto = new SessionDTO();
        dto.setSessionID(session.getSessionID());
        dto.setDate(session.getDate());
        dto.setDuration(session.getDuration());
        dto.setName(session.getName());
        return dto;
    }

    public static List<SessionDTO> fromSessions(List<Session> sessions) {
        return sessions.stream().map(SessionDTO::fromSession).toList();
    }

}
