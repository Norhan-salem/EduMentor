package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByEmail implements INotificationObserver{
    public String printNotifyMethod(){
        return "Email Notification";
    }

    @Override
    public boolean update() {
        return false;
    }
}
