package com.asu.EduMentor.socialMediaNotifier;

public interface INotificationManager {//this is our subject
    public  boolean addObserver(INotificationObserver observer);
    public boolean removeObserver(INotificationObserver observer);
    public boolean notifyObserver();

}
