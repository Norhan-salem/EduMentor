package com.asu.EduMentor.socialMediaNotifier;

import java.util.ArrayList;

public class NotificationService implements INotificationManager {
    private static NotificationService notificaton;
    private ArrayList<INotificationObserver> observers;

    private NotificationService() {
        observers = new ArrayList<INotificationObserver>();
    }

    public static NotificationService getInstance() {
        if (notificaton == null) {
            notificaton = new NotificationService();
        }
        return notificaton;
    }

    @Override
    public boolean addObserver(INotificationObserver observer) {
        if (observer == null) {
            return false;
        }
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(INotificationObserver observer) {
        return observers.remove(observer);
    }

    @Override
    public boolean notifyObserver(String content) {
        boolean error = true;
        for (INotificationObserver observer : this.observers) {
            if (!observer.update(content)) {
                error = false;
            }
        }
        return error;
    }
}
