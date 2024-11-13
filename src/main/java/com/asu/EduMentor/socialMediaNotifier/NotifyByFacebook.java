package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByFacebook implements INotificationObserver{
    INotificationManager facebook;
    public NotifyByFacebook(INotificationManager facebook){
        this.facebook = facebook;
        facebook.addObserver(this);
    }

    public String printNotifyMethod(){
        return "FaceBook Notification";
    }
    @Override
    public boolean update() {
        return false;
    }
}
