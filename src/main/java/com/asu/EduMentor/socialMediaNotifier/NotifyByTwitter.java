package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByTwitter implements INotificationObserver{
    public String printNotifyMethod(){
        return "Twitter Notification";
    }
    @Override
    public boolean update() {
        return false;
    }
}
