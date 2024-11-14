package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByEmail implements INotificationObserver{
    INotificationManager email;
    public NotifyByEmail(INotificationManager email){
        this.email = email;
        email.addObserver(this);
    }

    public String printNotifyMethod(){
        return "Email Notification";
    }

    @Override
    public boolean update() {
        return false;
    }
}
