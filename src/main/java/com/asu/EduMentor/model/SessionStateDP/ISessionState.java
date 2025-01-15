package com.asu.EduMentor.model.SessionStateDP;

import com.asu.EduMentor.model.Session;

public interface ISessionState{
    public void nextState(Session session);
}
