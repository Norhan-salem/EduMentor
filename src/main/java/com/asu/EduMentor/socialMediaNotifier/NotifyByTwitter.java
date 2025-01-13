package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByTwitter implements INotificationObserver{
    INotificationManager twitter;
    public NotifyByTwitter(INotificationManager twitter){
        this.twitter = twitter;
        twitter.addObserver(this);
    }

    public String printNotifyMethod(){
        return "Twitter Notification";
    }
    @Override
    public boolean update() {
        printNotifyMethod();
        return false;
    }
}
