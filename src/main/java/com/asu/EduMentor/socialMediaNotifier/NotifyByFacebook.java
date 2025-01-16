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
    public boolean update(String content) {
        try {
            return facebookNotificationFacade.createPost(content);
        } catch (Exception e) {
            return false;
        }
    }
}
