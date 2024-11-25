package com.asu.EduMentor.socialMediaNotifier;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface INotificationObserver {
    public boolean update() throws UnirestException;
}
