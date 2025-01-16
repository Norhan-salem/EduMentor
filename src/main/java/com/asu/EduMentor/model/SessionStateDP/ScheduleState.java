package com.asu.EduMentor.model.SessionStateDP;

import com.asu.EduMentor.model.Session;

public class ScheduleState implements ISessionState{

    @Override
    public void nextState(Session session) {
        session.setSessionState(new OngoingState());
    }

    @Override
    public String getStateName() {
        return "Scheduled";
    }
}
