package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByFacebook implements INotificationObserver{
    public String printNotifyMethod(){
        return "FaceBook Notification";
    }
    @Override
    public boolean update() {
        return false;
    }
}
