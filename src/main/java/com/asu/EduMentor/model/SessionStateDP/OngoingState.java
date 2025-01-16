package com.asu.EduMentor.model.SessionStateDP;

import com.asu.EduMentor.model.Session;

public class OngoingState implements ISessionState{

    @Override
    public void nextState(Session session) {
        session.setSessionState(new CompletedState());
    }

    @Override
    public String getStateName() {
        return "Ongoing";
    }
}
