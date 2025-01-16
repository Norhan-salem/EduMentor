package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByFacebook implements INotificationObserver{
    INotificationManager facebook;
    FacebookNotificationFacade facebookNotificationFacade;
    public NotifyByFacebook(INotificationManager notificationManager){
        this.facebook = notificationManager;
        notificationManager.addObserver(this);
        facebookNotificationFacade = new FacebookNotificationFacade();
    }

    @Override
    public boolean update() {
        try {
            return facebookNotificationFacade.createPost("A new session has been created");
        } catch (Exception e) {
            return false;
        }
    }
}
