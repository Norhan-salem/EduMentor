package com.asu.EduMentor.socialMediaNotifier;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface INotificationManager {//this is our subject
    public  boolean addObserver(INotificationObserver observer);
    public boolean removeObserver(INotificationObserver observer);
    public boolean notifyObserver() throws UnirestException;

}
